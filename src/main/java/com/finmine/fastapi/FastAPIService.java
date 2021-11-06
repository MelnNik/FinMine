package com.finmine.fastapi;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FastAPIService {

    @Bean
    public WebClient localApiClient() {
        return WebClient.create("http://localhost:8000/");
    }


}
