package com.example.observableweb.observation;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import org.springframework.stereotype.Component;

//@Component
public class SimpleHandler implements ObservationHandler<Observation.Context> {

    @Override
    public void onStart(Observation.Context context) {
        System.out.println("Start " + context.get(String.class));
    }

    @Override
    public void onStop(Observation.Context context) {
        System.out.println("Stop " + context.get(String.class));
    }

    @Override
    public void onError(Observation.Context context) {
        System.out.println("Error " + context.get(String.class) + ", error: " + context.getError());
    }

    @Override
    public void onEvent(Observation.Event event, Observation.Context context) {
        System.out.println("Event " + context.get(String.class) + ", event " + event);
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return true;
    }
}
