package com.example.observations_lecture;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import jakarta.servlet.http.HttpServletRequest;
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
    public String hello(@PathVariable String name, HttpServletRequest request) {
        return Observation.createNotStarted("testObservation", observationRegistry)
            .lowCardinalityKeyValue("name", name)
            .highCardinalityKeyValue("url", request.getRequestURL().toString())
            .observe(() -> "Hello World");
    }

    @GetMapping("/hello2")
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

        try (Observation.Scope scope = observation.openScope()) {
            observation.event(Observation.Event.of("test.event1", "something happened again"));
            doSomeWorkHere();
        } catch (Exception e) {
            observation.error(e);
            throw e;
        } finally {
            observation.stop();
        }

    }

    @Observed(name = "aop-observation")
    @GetMapping("/hello3/{name}")
    public String hello3(@PathVariable String name) {
        return "Hello " + name + "!";
    }

    private void doSomeWorkHere() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
