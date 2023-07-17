package com.glaze.flow.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    @Async
    public void sendAccountVerificationEmail(String email, Long userId) {

    }

}
