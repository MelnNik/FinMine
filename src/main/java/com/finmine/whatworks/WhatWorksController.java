package com.finmine.whatworks;

import com.finmine.fastapi.FastAPIService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class WhatWorksController {

    private final FastAPIService fastAPIService;


    @GetMapping("/fastapi/works")
    public String MainWorks() {
        String result = fastAPIService.localApiClient().get().uri("/works").retrieve().bodyToMono(String.class).block();
        return result;
    }

    @GetMapping("/fastapi/works/strategy")
    public String WorksStrategy(@RequestParam String strategy) {
        String result = fastAPIService.localApiClient().get().uri("/works").retrieve().bodyToMono(String.class).block();
        return result;
    }
}
