package com.example.observableweb.rest;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AvengerController {

    private final ObservationRegistry observationRegistry;
    private final MeterRegistry meterRegistry;

    public AvengerController(ObservationRegistry observationRegistry, MeterRegistry meterRegistry) {
        this.observationRegistry = observationRegistry;
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("/observe")
    public void observeMethod() {
        Observation.createNotStarted("observeTest", observationRegistry)
            .lowCardinalityKeyValue("lowTag", "lowValue")
            .highCardinalityKeyValue("highTag", "highValue")
            .observe(() -> System.out.println("Testing observation"));
    }

    @GetMapping("/observe2")
    public void observe2() {
        Observation.createNotStarted("my.operation", observationRegistry)
            .observe(this::doSomeWorkHere);
    }

    @GetMapping("/observe3")
    public void observe3() {
        Timer.Sample timer = Timer.start(meterRegistry);
        doSomeWorkHere();
        timer.stop(Timer.builder("my.timer").register(meterRegistry));
    }

    private void doSomeWorkHere() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
