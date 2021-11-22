package com.finmine.industry;

import com.finmine.fastapi.FastAPIService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class IndustryController {
    private final FastAPIService fastAPIService;
    private final IndustryRepository industryRepository;
    private final IndustryService industryService;


    @GetMapping("/industry")
    public String Industry(@RequestParam String name) {
        Mono<String> result = fastAPIService.localApiClient().get().uri("/" + name).retrieve().bodyToMono(String.class);
        return name + " ! " + result;
    }
}
