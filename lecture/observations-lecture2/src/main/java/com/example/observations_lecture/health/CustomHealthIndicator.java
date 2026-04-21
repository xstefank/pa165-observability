package com.example.observations_lecture.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    private final Random random = new Random();

    @Override
    public Health health() {
        return random.nextBoolean() ? Health.up().build() :
            Health.down()
            .withDetail("reason", "Randomly unhealthy")
            .build();
    }
}
