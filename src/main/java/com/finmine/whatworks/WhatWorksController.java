package com.finmine.whatworks;

import com.finmine.fastapi.FastAPIService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@AllArgsConstructor
public class WhatWorksController {
    private final WhatWorksService whatWorksService;
    private final WhatWorksStrategyRepository whatWorksStrategyRepository;
    private final WhatWorksStrategyTickersRepository whatWorksStrategyTickersRepository;

    // Main Table view, pulls data from WhatWorksTable
    @GetMapping("/works")
    public String WhatWorks() {
        return whatWorksService.mainWhatWorks();
    }

    // View for each strategy, pulls data from WhatWorksStrategy on specific strategy request
    @GetMapping("/works/{strategy}")
    public String WorksStrategy(@PathVariable String strategy) {
        return whatWorksService.strategyWhatWorks();
    }


    // Post data from all WhatWorksStrategy strategies and get a table to populate WhatWorksTable
    @PostMapping("/fastapi/works")
    public String WhatWorksPost() {
        return whatWorksService.whatWorksPost();
    }

    // Get data for each strategy and populate WhatWorksStrategy by strategy
    // send request, if successful response then create in db by name(path variable) with list of tickers and prices to buy(prices from fastapi as well)
    @GetMapping("/fastapi/works/{strategy}")
    // TODO: move code to service
    public String GetWorksStrategy(@PathVariable String strategy) {
        // TODO: ONCE STRATEGY IS IN AND ONLY UPDATE IT
        String tickersList = whatWorksService.getStrategyWhatWorks(strategy).replaceAll("[-+.^:\"\\[\\]]", "");
        String[] data = tickersList.split(",");
        // For element in a list -> populate db and get price here as well
        WhatWorksStrategy whatWorksStrategy = new WhatWorksStrategy(strategy);
        whatWorksStrategy.setName("long_25");
        whatWorksStrategyRepository.save(whatWorksStrategy);
        for (String str : data) {
            whatWorksStrategyTickersRepository.save(new WhatWorksStrategyTickers(str, 5.4, new Random().nextDouble(), whatWorksStrategy));
        }
        return Arrays.toString(data);
    }
}
