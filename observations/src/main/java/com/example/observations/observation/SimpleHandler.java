package com.example.observations.observation;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;

public class SimpleHandler implements ObservationHandler<Observation.Context> {

    @Override
    public void onStart(Observation.Context context) {
        System.out.println("Start " + context.getName());
    }

    @Override
    public void onStop(Observation.Context context) {
        System.out.println("Stop " + context.getName());
    }

    @Override
    public void onError(Observation.Context context) {
        System.out.println("Error " + context.getName() + ", error: " + context.getError());
    }

    @Override
    public void onEvent(Observation.Event event, Observation.Context context) {
        System.out.println("Event " + context.getName() + ", event " + event);
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return true;
    }
}