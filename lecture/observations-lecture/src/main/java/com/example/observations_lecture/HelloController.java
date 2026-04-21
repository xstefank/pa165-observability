package com.example.observations_lecture;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final ObservationRegistry observationRegistry;
    private final RestTemplateBuilder restTemplateBuilder;

    public HelloController(ObservationRegistry observationRegistry, RestTemplateBuilder restTemplateBuilder) {
        this.observationRegistry = observationRegistry;
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name, HttpServletRequest request) {
        restTemplateBuilder.rootUri("http://localhost:8081").build().getForObject("/hello/Lolo", String.class);
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

        Observation observation2 = Observation.start("my.operation2", observationRegistry);
        try (Observation.Scope scope = observation2.openScope()) {
            observation2.event(Observation.Event.of("test.event1", "something happened again"));
            doSomeWorkHere();
        } catch (Exception e) {
            observation2.error(e);
            throw e;
        } finally {
            observation2.stop();
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
