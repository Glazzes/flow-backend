package com.glaze.flow.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public String login(Model model) {
        Map<String, Object> attributes = model.asMap();
        System.out.println(attributes.toString());

        return "authorization/login";
    }

}
