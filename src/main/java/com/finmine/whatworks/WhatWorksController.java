package com.finmine.whatworks;

import com.finmine.fastapi.FastAPIService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WhatWorksController {
    private final WhatWorksService whatWorksService;

    // Main Table view
    @GetMapping("/works")
    public String WhatWorks() {
        return whatWorksService.mainWhatWorks();
    }

    // View for each strategy
    @GetMapping("/works/{strategy}")
    public String WorksStrategy(@PathVariable String strategy) {
        return whatWorksService.strategyWhatWorks();
    }


    @PostMapping("/fastapi/works")
    public String WhatWorksPost() {
        return whatWorksService.whatWorksPost();
    }

    @GetMapping("/fastapi/works/{strategy}")
    public String GetWorksStrategy(@PathVariable String strategy) {
        return whatWorksService.getStrategyWhatWorks(strategy);
    }
}
