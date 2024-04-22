package com.example.observations.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {

    private Logger logger = LoggerFactory.getLogger(LoggingController.class);

    @GetMapping("/log")
    public void log() {
        logger.trace("TRACE test");
        logger.debug("DEBUG test");
        logger.info("INFO test");
        logger.warn("WARN test");
        logger.error("ERROR test");
    }
}
