package com.example.observations.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
//        if (databaseUpCheck()) {
            return Health.up().build();
//        } else {
//            return Health.down().build();
//        }
    }


    private static boolean databaseUpCheck() {
        return new Random().nextBoolean();
    }
}
