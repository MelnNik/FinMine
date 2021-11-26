package com.finmine.whatworks;

import com.finmine.whatworks.strategy.WhatWorksStrategy;
import com.finmine.whatworks.strategy.WhatWorksStrategyRepository;
import com.finmine.whatworks.strategy.WhatWorksStrategyTickersRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class WhatWorksController {
    private final WhatWorksService whatWorksService;

    // Main Table view, pulls data from WhatWorksTable
    @GetMapping("/works")
    public @ResponseBody
    Iterable<WhatWorksStrategy> WhatWorks() {
        return whatWorksService.WhatWorks();
    }

    @GetMapping("/works/{strategy}")
    public @ResponseBody
    WhatWorksStrategy WorksStrategy(@PathVariable String strategy) {

        return whatWorksService.WorksStrategy(strategy);

    }


    @GetMapping("/fastapi/works/{strategy}")
    public String GetWorksStrategy(@PathVariable String strategy) {

        return whatWorksService.getStrategyWhatWorks(strategy);


    }
}
