package com.example.observations;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final ObservationRegistry observationRegistry;

    public HelloController(ObservationRegistry observationRegistry) {
        this.observationRegistry = observationRegistry;
    }

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        return Observation.createNotStarted("testObservation", observationRegistry)
            .lowCardinalityKeyValue("name", name)
            .highCardinalityKeyValue("url", "TODO")
            .observe(() -> "Hello, " + name + "!");
    }

    @Observed(name = "aop-observation")
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }

    @GetMapping("/observe")
    public void observe5() {
        Observation observation = Observation.start("my.operation", observationRegistry);
        try (Observation.Scope scope = observation.openScope()) {
            observation.event(Observation.Event.of("test.event", "something happened"));
            doSomeWorkHere();
        } catch (Exception e) {
            observation.error(e);
            throw e;
        } finally {
            observation.stop();
        }
    }

    private void doSomeWorkHere() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
