package com.glaze.flow.services;

import com.glaze.flow.entities.Otp;
import com.glaze.flow.entities.User;
import com.glaze.flow.enums.OTPType;
import com.glaze.flow.events.SendEmailVerificationEvent;
import com.glaze.flow.repositories.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OTPService {

    private final static Logger LOGGER = LoggerFactory.getLogger(OTPService.class);
    private final OtpRepository otpRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void saveAccountActivationToken(User user) {
        LOGGER.info("Issuing new account verification token for user with id {}", user.getId());
        String token = UUID.randomUUID().toString();
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1L);

        Otp accountVerificationOTP = Otp.builder()
            .id(null)
            .token(token)
            .type(OTPType.ACCOUNT_ACTIVATION)
            .expiresAt(tomorrow)
            .user(user)
            .build();

        otpRepository.save(accountVerificationOTP);
        LOGGER.info("An account verification token has been created successfully for user, id {}", user.getId());

        eventPublisher.publishEvent(new SendEmailVerificationEvent(user, token));
    }

}
