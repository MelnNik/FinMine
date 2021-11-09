package com.finmine.whatworks;

import com.finmine.utilities.GetStockPrice;
import com.finmine.whatworks.strategy.WhatWorksStrategy;
import com.finmine.whatworks.strategy.WhatWorksStrategyRepository;
import com.finmine.whatworks.strategy.WhatWorksStrategyTickers;
import com.finmine.whatworks.strategy.WhatWorksStrategyTickersRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
public class WhatWorksController {
    private final WhatWorksService whatWorksService;
    private final WhatWorksStrategyRepository whatWorksStrategyRepository;
    private final WhatWorksStrategyTickersRepository whatWorksStrategyTickersRepository;

    // TODO: move code to service
    // Main Table view, pulls data from WhatWorksTable
    // TODO: update performance of each strategy depending on price
    @GetMapping("/works")
    public @ResponseBody
    Iterable<WhatWorksStrategy> WhatWorks() {

        Iterable<WhatWorksStrategy> currentStrategy = whatWorksStrategyRepository.findAll();

        for (WhatWorksStrategy strat : currentStrategy) {
            List<WhatWorksStrategyTickers> tickers = strat.getWhatWorksStrategyTickers();
            Double counter = (double) 0;
            for (WhatWorksStrategyTickers ticker : tickers) {
                counter += ticker.getBuyPrice() / ticker.getCurrentPrice();
            }
            try {
                strat.setPerformance(strat.getPerformance() + counter);
                whatWorksStrategyRepository.save(strat);
            } catch (NullPointerException nullPointerException) {
                strat.setPerformance(counter);
                whatWorksStrategyRepository.save(strat);
            }
        }

        return whatWorksStrategyRepository.findAll();
    }

    // View for each strategy, pulls data from WhatWorksStrategy on specific strategy request
    @GetMapping("/works/{strategy}")
    public @ResponseBody
    WhatWorksStrategy WorksStrategy(@PathVariable String strategy) {
        return whatWorksStrategyRepository.findByName(strategy);
    }

    // Get data for each strategy and populate WhatWorksStrategy by strategy
    // send request, if successful response then create in db by name(path variable) with list of tickers and prices to buy(prices from fastapi as well)
    @GetMapping("/fastapi/works/{strategy}")

    public String GetWorksStrategy(@PathVariable String strategy) {

        String tickersList = whatWorksService.getStrategyWhatWorks(strategy).replaceAll("[-+.^:\"\\[\\]]", "");
        String[] data = tickersList.split(",");

        WhatWorksStrategy currentStrategy = whatWorksStrategyRepository.findByName(strategy);
        try {
            List<WhatWorksStrategyTickers> currentStrategyList = currentStrategy.getWhatWorksStrategyTickers();
            Set<String> tickersCurrentlyIn = new HashSet<>();
            for (WhatWorksStrategyTickers strategy1 : currentStrategyList) {
                tickersCurrentlyIn.add(strategy1.getTicker());
            }
            Set<String> incomingTickers = new HashSet<>(Arrays.asList(data));
            if (!tickersCurrentlyIn.equals(incomingTickers)) {

                Set<String> copyTickersCurrentlyIn = new HashSet<>(tickersCurrentlyIn);
                Set<String> copyIncomingTickers = new HashSet<>(incomingTickers);
                copyIncomingTickers.removeAll(tickersCurrentlyIn);
                copyTickersCurrentlyIn.removeAll(incomingTickers);
                for (String str : copyTickersCurrentlyIn) {
                    Double currentPrice = GetStockPrice.getCurrentPrice(str);
                    Double boughtPrice = whatWorksStrategyTickersRepository.findWhatWorksStrategyTickersByTicker(str).getBuyPrice();
                    currentStrategy.setPerformance(currentStrategy.getPerformance() + currentPrice / boughtPrice); // TODO: FIX
                    whatWorksStrategyRepository.save(currentStrategy);
                    whatWorksStrategyTickersRepository.deleteById(whatWorksStrategyTickersRepository.findWhatWorksStrategyTickersByTicker(str).getId());
                    // TODO: ADD (buyPrice-currentPrice) - PERFORMANCE TO THIS STRATEGY'S PERFORMANCE
                }
                for (String str : copyIncomingTickers) {
                    Double currentPrice = GetStockPrice.getCurrentPrice(str);
                    whatWorksStrategyTickersRepository.save(
                            new WhatWorksStrategyTickers(str, currentPrice, currentPrice, whatWorksStrategyRepository.findByName(strategy)));
                }
                return "Updated";
            }
            return "No changes";
        } catch (NullPointerException nullPointerException) {
            WhatWorksStrategy whatWorksStrategy = new WhatWorksStrategy(strategy);
            whatWorksStrategy.setName(strategy);
            whatWorksStrategyRepository.save(whatWorksStrategy);
            for (String str : data) {
                // TODO: Get price
                Double currentPrice = GetStockPrice.getCurrentPrice(str);
                whatWorksStrategyTickersRepository.save(new WhatWorksStrategyTickers(str, currentPrice, currentPrice, whatWorksStrategy));
            }
            return Arrays.toString(data);
        }


    }
}
