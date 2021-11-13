package com.finmine.utilities;

import com.finmine.whatworks.strategy.WhatWorksStrategy;
import com.finmine.whatworks.strategy.WhatWorksStrategyRepository;
import com.finmine.whatworks.strategy.WhatWorksStrategyTickers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Scanner;

// TODO: scheduled Service for parsing through all tickers in strategies and update their prices + performance(implement getStockPrice)
// CurrentPriceUpdater + Performance updater

@AllArgsConstructor
public class CurrentPriceChecker {
    // get all strategytickers; for each element update current price and update strategy's performance
    private final WhatWorksStrategyRepository whatWorksStrategyRepository;

    public void PerformanceUpdater() {
        Iterable<WhatWorksStrategy> currentStrategy = whatWorksStrategyRepository.findAll();

        for (WhatWorksStrategy strat : currentStrategy) {
            List<WhatWorksStrategyTickers> tickers = strat.getWhatWorksStrategyTickers();
            Double counter = (double) 0;
            for (WhatWorksStrategyTickers ticker : tickers) {
                counter += ticker.getBuyPrice() / (ticker.getCurrentPrice() - ticker.getBuyPrice());
            }
            // TODO: update performance of each strategy depending on price IN CURRENT PRICE CHECKER
            try {
                strat.setPerformance(strat.getPerformance() + counter);
                whatWorksStrategyRepository.save(strat);
            } catch (NullPointerException nullPointerException) {
                strat.setPerformance(counter);
                whatWorksStrategyRepository.save(strat);
            }
        }
    }
}
