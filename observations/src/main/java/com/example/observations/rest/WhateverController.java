package com.example.observations.rest;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WhateverController {

    private final ObservationRegistry observationRegistry;


    public WhateverController(ObservationRegistry observationRegistry) {
        this.observationRegistry = observationRegistry;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "World") String name) {
        return Observation.createNotStarted("byName", observationRegistry)
            .observe(() -> "hello %s!".formatted(name));
    }
}
