package com.example.observableweb.rest;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceAController {

    private final RestTemplateBuilder restTemplateBuilder;

    public ServiceAController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @GetMapping("/call")
    public String callServiceB() {
        return restTemplateBuilder
            .rootUri("http://localhost:8081")
            .build()
            .getForObject("/hello", String.class);
    }
}
