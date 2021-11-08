package com.finmine.investors;

import com.finmine.fastapi.FastAPIService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class InvestorsController {
    private final FastAPIService fastAPIService;


    @GetMapping("/investors")
    public String Investors(@RequestParam String name) {
        Mono<String> result = fastAPIService.localApiClient().get().uri("/" + name).retrieve().bodyToMono(String.class);
        return name + " ! " + result;
    }
}
