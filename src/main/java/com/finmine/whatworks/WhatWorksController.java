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

    // Main Table view, pulls data from WhatWorksTable
    @GetMapping("/works")
    public @ResponseBody
    Iterable<WhatWorksStrategy> WhatWorks() {
        return whatWorksStrategyRepository.findAll();
    }

    @GetMapping("/works/{strategy}")
    public @ResponseBody
    WhatWorksStrategy WorksStrategy(@PathVariable String strategy) {

        return whatWorksStrategyRepository.findByName(strategy);

    }


    @GetMapping("/fastapi/works/{strategy}")
    public String GetWorksStrategy(@PathVariable String strategy) {

        return whatWorksService.getStrategyWhatWorks(strategy);


    }
}
