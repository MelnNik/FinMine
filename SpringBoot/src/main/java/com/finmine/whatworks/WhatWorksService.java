package com.finmine.whatworks;

import com.finmine.fastapi.FastAPIService;
import com.finmine.utilities.GetStockPrice;
import com.finmine.whatworks.strategy.WhatWorksStrategy;
import com.finmine.whatworks.strategy.WhatWorksStrategyRepository;
import com.finmine.whatworks.strategy.WhatWorksStrategyTickers;
import com.finmine.whatworks.strategy.WhatWorksStrategyTickersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class WhatWorksService {

    private final FastAPIService fastAPIService;
    private final WhatWorksStrategyRepository whatWorksStrategyRepository;
    private final WhatWorksStrategyTickersRepository whatWorksStrategyTickersRepository;


    public Iterable<WhatWorksStrategy> WhatWorks() {
        return whatWorksStrategyRepository.findAll();
    }

    public WhatWorksStrategy WorksStrategy(String strategy) {

        return whatWorksStrategyRepository.findByName(strategy);

    }

    // Get tickers for a particular strategy
    public String getStrategyWhatWorks(String strategy) {
        String tickersList =
                Objects.requireNonNull(fastAPIService.localApiClient().get().uri("/works/" + strategy).retrieve().bodyToMono(String.class).block())
                        .replaceAll("[-+.^:\"\\[\\]]", "");
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
                    currentStrategy.setPerformance(currentStrategy.getPerformance() + ((currentPrice - boughtPrice) / boughtPrice) * 100);
                    whatWorksStrategyRepository.save(currentStrategy);
                    whatWorksStrategyTickersRepository.deleteById(whatWorksStrategyTickersRepository.findWhatWorksStrategyTickersByTicker(str).getId());
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
