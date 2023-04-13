package com.example.serviceB.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceBController {

    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "false") boolean fail) throws Exception {
        if (fail) {
            throw new Exception("Failing on request");
        }
        return "Hello from ServiceB";
    }
}
