package com.example.observations;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WhateverController {

    private final ObservationRegistry observationRegistry;
    private final RestTemplateBuilder restTemplateBuilder;

    public WhateverController(ObservationRegistry observationRegistry, RestTemplateBuilder restTemplateBuilder) {
        this.observationRegistry = observationRegistry;
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(defaultValue = "World") String name) {
        String forObject = restTemplateBuilder
            .rootUri("http://localhost:8081")
            .build()
            .getForObject("/hello", String.class);

        System.out.println(forObject);

        return Observation.createNotStarted("byName", observationRegistry)
            .lowCardinalityKeyValue("name", name)
            .observe(() -> "hello " + name + "!");
    }
}
