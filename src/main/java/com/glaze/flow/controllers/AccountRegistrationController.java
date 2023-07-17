package com.glaze.flow.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountRegistrationController {

    @GetMapping("/account-registration")
    public String save(
        @RequestParam(defaultValue = "Glaze") String name,
        Model modeal
    ) {
        modeal.addAttribute("username", name);
        return "/account-registration-email-sent";
    }

}
