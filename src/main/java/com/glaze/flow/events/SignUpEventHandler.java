package com.glaze.flow.events;

import com.glaze.flow.services.EmailService;
import com.glaze.flow.services.OTPService;
import com.glaze.flow.services.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpEventHandler {

    private final EmailService emailService;
    private final OTPService otpService;
    private final UserDetailsService userDetailsService;

    @EventListener
    public void handleSignUp(SignUpEvent signUpEvent) {
        userDetailsService.save(signUpEvent.user());
        OTPService.AccountVerification result = otpService.saveAccountActivationToken(signUpEvent.user());
        emailService.sendAccountVerificationEmail(result.user(), result.token());
    }

}
