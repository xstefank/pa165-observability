package com.example.observations.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CustomHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
//        if (check()) {
            return Health.up().withDetail("healthy", "yes").build();
//        } else {
//            return Health.down(new Exception("failed to perform check")).build();
//        }
    }

    private boolean check() {
        return new Random().nextBoolean();
    }
}
