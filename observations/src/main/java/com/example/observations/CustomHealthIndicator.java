package com.example.observations;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CustomHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        return check() ? Health.up().build() :
            Health.down()
                .withDetail("Error", "Custom error message").build();
    }

    final Random random = new Random();

    private boolean check() {
        return random.nextBoolean();
    }
}
