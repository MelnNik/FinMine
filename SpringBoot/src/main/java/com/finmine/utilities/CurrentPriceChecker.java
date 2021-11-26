package com.finmine.utilities;

import com.finmine.whatworks.strategy.WhatWorksStrategy;
import com.finmine.whatworks.strategy.WhatWorksStrategyRepository;
import com.finmine.whatworks.strategy.WhatWorksStrategyTickers;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CurrentPriceChecker {
    private final WhatWorksStrategyRepository whatWorksStrategyRepository;

    public void PerformanceUpdaterWhatWorks() {
        Iterable<WhatWorksStrategy> currentStrategy = whatWorksStrategyRepository.findAll();

        for (WhatWorksStrategy strat : currentStrategy) {
            List<WhatWorksStrategyTickers> tickers = strat.getWhatWorksStrategyTickers();
            Double counter = (double) 0;
            for (WhatWorksStrategyTickers ticker : tickers) {
                counter += ((ticker.getCurrentPrice() - ticker.getBuyPrice()) / ticker.getBuyPrice()) * 100;
            }
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
