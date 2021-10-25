package com.finmine.fastapi;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class FastAPIController {


    private final FastAPIService fastAPIService;


    @GetMapping("/fastapi")
    public String mainFastAPI() {
        return fastAPIService.localApiClient().get().uri("/").retrieve().bodyToMono(String.class).block();
    }
}
