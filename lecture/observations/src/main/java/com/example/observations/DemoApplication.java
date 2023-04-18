package com.example.observations;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext appContext = SpringApplication.run(DemoApplication.class, args);
		ObservationRegistry observationRegistry = appContext.getBean(ObservationRegistry.class);
		observationRegistry.observationConfig().observationHandler(new CustomObservationHandler());
	}

}
