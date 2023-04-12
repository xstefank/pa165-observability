package com.example.observableweb.rest;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomMetricsController {

    private final MeterRegistry meterRegistry;

    public CustomMetricsController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        Counter counter = this.meterRegistry.counter("test-counter");
        counter.increment();
    }

    @GetMapping("/customMeter")
    public void customMeter() {
        Counter counter = meterRegistry.counter("test-counter");
        counter.increment();
    }
}
