package com.glaze.flow.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestingController {

    @GetMapping("/api/home")
    public String home() {
        return "Hello world";
    }

}
