package com.example.observableweb;

import com.example.observableweb.rest.ObservableService;
import io.micrometer.observation.tck.TestObservationRegistry;
import io.micrometer.observation.tck.TestObservationRegistryAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootTest
@EnableAutoConfiguration
@AutoConfigureObservability
public class ObservabilityTest {

    @Autowired
    ObservableService service;
    @Autowired
    TestObservationRegistry registry;

    @TestConfiguration
    static class ObservationTestConfiguration {

        @Bean
        TestObservationRegistry observationRegistry() {
            return TestObservationRegistry.create();
        }
    }

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
