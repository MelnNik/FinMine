package com.finmine.whatworks;

import com.finmine.fastapi.FastAPIService;
import com.finmine.whatworks.strategy.WhatWorksStrategyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WhatWorksService {

    private final FastAPIService fastAPIService;
    private final WhatWorksStrategyRepository whatWorksStrategyRepository;


    // Pull the main table for what works from the db
    public String mainWhatWorks() {
        return "current table";
    }

    public String strategyWhatWorks() {
        return "current strat";
    }

    // Post data from db to fastapi works main view with main strategies and create a table from it to show in mainWhatWorks
    public String whatWorksPost() {
        return fastAPIService.localApiClient().get().uri("/works").retrieve().bodyToMono(String.class).block();
    }

    // Get tickers for a particular strategy
    public String getStrategyWhatWorks(String strategy) {
        return fastAPIService.localApiClient().get().uri("/works/" + strategy).retrieve().bodyToMono(String.class).block();
    }

}
