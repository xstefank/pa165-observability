package com.example.observations.health;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CustomHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        return check() ? Health.up().withDetail("key", "value").build() :
            Health.down(new Exception("failed health indicator")).build();
    }

    private boolean check() {
        return new Random().nextBoolean();
    }
}
