package com.example.observations;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ObservableService {

    private final ObservationRegistry observationRegistry;
    private final MeterRegistry meterRegistry;
    private final RestTemplateBuilder restTemplateBuilder;

    public ObservableService(ObservationRegistry observationRegistry, MeterRegistry meterRegistry, RestTemplateBuilder restTemplateBuilder) {
        this.observationRegistry = observationRegistry;
        this.meterRegistry = meterRegistry;
        this.restTemplateBuilder = restTemplateBuilder;
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

    @GetMapping("/observe4")
    public void observe4() {
        Observation.Context context = new Observation.Context().put(String.class, "test-value");
        Observation.createNotStarted("test.context", () -> context, observationRegistry)
            .observe(this::doSomeWorkHere);
    }

    @GetMapping("/observe5")
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

    @GetMapping("/observe6")
    @Observed(name = "my.observation")
    public void observe6() {
        doSomeWorkHere();
    }

    @GetMapping("/observe7")
    public void observe7() {
        Observation.createNotStarted("to.ignore", observationRegistry)
            .observe(this::doSomeWorkHere);
    }

    @GetMapping("/observe8/{name}")
    public void observe8(HttpServletRequest request, @PathVariable String name) {
        Observation.createNotStarted("observe8", observationRegistry)
            .lowCardinalityKeyValue("name", name)
            .lowCardinalityKeyValue("uri", request.getRequestURI())
            .observe(this::doSomeWorkHere);
    }

    @GetMapping("/observe9")
    public String observe9() {
        String response = restTemplateBuilder
            .rootUri("http://localhost:8081")
            .build()
            .getForObject("/observe", String.class);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Observation observation = Observation.start("inner.span", observationRegistry);
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

    private void doSomeWorkHere() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
