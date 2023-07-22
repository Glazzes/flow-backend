package com.glaze.flow.services;

import com.glaze.flow.entities.OTP;
import com.glaze.flow.entities.User;
import com.glaze.flow.enums.OTPType;
import com.glaze.flow.repositories.OTPRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OTPService {

    private final static Logger LOGGER = LoggerFactory.getLogger(OTPService.class);
    private final OTPRepository otpRepository;

    public AccountVerification saveAccountActivationToken(User user) {
        LOGGER.info("Issuing new account verification token for user with id {}", user.getId());
        String token = UUID.randomUUID().toString();
        LocalDateTime tomorrow = LocalDateTime.now()
            .plus(1L, ChronoUnit.DAYS);

        OTP accountVerificationOTP = OTP.builder()
            .id(null)
            .token(token)
            .type(OTPType.ACCOUNT_ACTIVATION)
            .expiresAt(tomorrow)
            .user(user)
            .build();

        otpRepository.save(accountVerificationOTP);
        LOGGER.info("An account verification token has been created successfully for user, id {}", user.getId());

        return new AccountVerification(user, token);
    }

    public record AccountVerification(
        User user,
        String token
    ){}

}
