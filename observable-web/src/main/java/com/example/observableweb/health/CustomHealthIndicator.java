package com.example.observableweb.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CustomHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        return check() ? Health.up().build() : Health.down(new Exception("failed health indicator")).build();
    }

    private boolean check() {
        return new Random().nextBoolean();
    }
}
