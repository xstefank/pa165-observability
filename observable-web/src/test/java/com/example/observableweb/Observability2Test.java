package com.example.observableweb;

import com.example.observableweb.rest.ObservableService;
import io.micrometer.observation.tck.TestObservationRegistry;
import io.micrometer.observation.tck.TestObservationRegistryAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableAutoConfiguration
@EnableTestObservation
public class Observability2Test {

    @Autowired
    ObservableService service;
    @Autowired
    TestObservationRegistry registry;

    @Test
    void testObservation() {
        service.observeMethod();
        TestObservationRegistryAssert.assertThat(registry)
            .hasObservationWithNameEqualTo("observeTest")
            .that()
            .hasBeenStarted()
            .hasBeenStopped()
            .hasLowCardinalityKeyValue("lowTag", "lowValue")
            .hasHighCardinalityKeyValue("highTag", "highValue");
    }
}
