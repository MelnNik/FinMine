package com.finmine.analytics;

import com.finmine.fastapi.FastAPIService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class AnalyticsController {
    private final FastAPIService fastAPIService;


    @GetMapping("/fastapi/finmine")
    public String MainAnalytics(@RequestParam String name) {
        Mono<String> result = fastAPIService.localApiClient().get().uri("/" + name).retrieve().bodyToMono(String.class);
        return name + " ! " + result;
    }
}
