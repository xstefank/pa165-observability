package com.example.observableweb.rest;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceAController {

    private final RestTemplateBuilder restTemplateBuilder;
    private final ObservationRegistry registry;

    public ServiceAController(RestTemplateBuilder restTemplateBuilder, ObservationRegistry registry) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.registry = registry;
    }

    @GetMapping("/call")
    public String callServiceB() {
        String response = restTemplateBuilder
            .rootUri("http://localhost:8081")
            .build()
            .getForObject("/hello", String.class);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Observation observation = Observation.start("inner.span", registry);
        try (Observation.Scope scope = observation.openScope()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            observation.error(e);
            throw e;
        } finally {
            observation.stop();
        }

        return response;
    }
}
