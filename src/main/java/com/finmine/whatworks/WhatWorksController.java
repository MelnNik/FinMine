package com.finmine.whatworks;

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
    @GetMapping("/works")
    public @ResponseBody
    Iterable<WhatWorksStrategy> WhatWorks() {
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
        // TODO: ONCE STRATEGY IS IN -> ONLY UPDATE IT; IF NEW TICKERS IN A STRATEGY ->
        //  STILL TAKE PAST PERFORMANCE INTO CONSIDERATION(on ticker update - performance +=)
        String tickersList = whatWorksService.getStrategyWhatWorks(strategy).replaceAll("[-+.^:\"\\[\\]]", "");
        String[] data = tickersList.split(",");
        // For element in a list -> populate db and get price here as well
        WhatWorksStrategy whatWorksStrategy = new WhatWorksStrategy(strategy);
        whatWorksStrategy.setName(strategy);
        whatWorksStrategyRepository.save(whatWorksStrategy);
        for (String str : data) {
            // TODO: Get price
            Double currentPrice = (Double) 5.4;
            whatWorksStrategyTickersRepository.save(new WhatWorksStrategyTickers(str, currentPrice, currentPrice, whatWorksStrategy));
        }
        return Arrays.toString(data);
    }
}
