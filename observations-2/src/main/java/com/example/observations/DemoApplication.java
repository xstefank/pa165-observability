package com.example.observations;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
//		ObservationRegistry observationRegistry = applicationContext.getBean(ObservationRegistry.class);
//		observationRegistry.observationConfig().observationHandler(new SimpleHandler());
//		observationRegistry.observationConfig().
	}

	@Bean
	public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
		return new ObservedAspect(observationRegistry);
	}

}
