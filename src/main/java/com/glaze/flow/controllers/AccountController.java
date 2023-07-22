package com.glaze.flow.controllers;

import com.glaze.flow.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    @GetMapping("/account-activation/{userId}")
    public String activateAccount(
        @PathVariable Long userId,
        @RequestParam(name = "t") String token
    ) {
        LOGGER.info("GET request at account activation endpoint userId {}", userId);

        accountService.activateAccount(userId, token);
        return "dummy";
    }

}
