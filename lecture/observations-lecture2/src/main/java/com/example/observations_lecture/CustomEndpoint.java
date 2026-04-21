package com.example.observations_lecture;

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
    public CustomData read() {
        logger.trace("AAAAAAAAAAAAAAAAAAAAAAAAAA");
        return new CustomData("Hello, World!", 42);
    }


    @WriteOperation
    public CustomData write(String text, int number) {
        return new CustomData(text + "!", ++number);
    }

    public record CustomData(String text, int number) {

    }

}
