package com.example.observations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "data")
public class CustomEndpoint {

    Logger logger = LoggerFactory.getLogger(CustomEndpoint.class);

    @ReadOperation(produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomData getData() {
        logger.debug("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        return new CustomData("whatever", 42);
    }

    @WriteOperation(produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomData echoRequestPlus1(String text, int counter) {
        return new CustomData(text + " 1", counter + 1);
    }

    public static final record CustomData(String text, int counter) {}
}
