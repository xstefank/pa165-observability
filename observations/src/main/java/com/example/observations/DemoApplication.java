package com.example.observations;

import com.example.observations.observation.SimpleHandler;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext appContext = SpringApplication.run(DemoApplication.class, args);
		ObservationRegistry observationRegistry = appContext.getBean(ObservationRegistry.class);
		observationRegistry.observationConfig().observationHandler(new SimpleHandler());
		observationRegistry.observationConfig().observationPredicate(
			(observationName, context) -> !"to.ignore".equals(observationName));
//		observationRegistry.observationConfig().observationFilter(context -> {
//			context.setName("modified");
//			return context;
//		});
	}

	@Bean
	public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
		return new ObservedAspect(observationRegistry);
	}
}
