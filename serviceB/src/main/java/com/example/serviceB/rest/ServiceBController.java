package com.example.serviceB.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceBController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from ServiceB";
    }
}
