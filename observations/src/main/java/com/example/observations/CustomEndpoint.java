package com.example.observations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "custom")
public class CustomEndpoint {

    Logger logger = LoggerFactory.getLogger(CustomEndpoint.class);

    @ReadOperation
    public CustomData getCustomData() {
        logger.error("getCustomData called");
        return new CustomData("whatever", 42);
    }

    @WriteOperation
    public CustomData writeCustomData(String text, int number) {
        return new CustomData(text + " updated", number + 1);
    }

    public static final record CustomData(String text, int number) {}
}
