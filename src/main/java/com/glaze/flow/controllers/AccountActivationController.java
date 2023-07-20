package com.glaze.flow.controllers;

import com.glaze.flow.services.AccountVerificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountActivationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountActivationController.class);

    private final AccountVerificationService accountVerificationService;

    @GetMapping("/account-activation/{userId}")
    public String activateAccount(@PathVariable Long userId) {
        LOGGER.info("Getting request at account activation endpoint for account with id {}", userId);
        accountVerificationService.activateAccount(userId);
        return "dummy";
    }

}
