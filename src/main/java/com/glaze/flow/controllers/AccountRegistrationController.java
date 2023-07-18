package com.glaze.flow.controllers;

import com.glaze.flow.services.AccountVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountRegistrationController {
    private final AccountVerificationService accountVerificationService;

    @GetMapping("/account-activation/{userId}")
    public String activateAccount(@PathVariable Long userId) {
        accountVerificationService.activateAccount(userId);
        return "dummy";
    }

}
