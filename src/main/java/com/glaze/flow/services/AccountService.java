package com.glaze.flow.services;

import com.glaze.flow.entities.OTP;
import com.glaze.flow.enums.OTPType;
import com.glaze.flow.exceptions.ResourceExpiredException;
import com.glaze.flow.exceptions.ResourceNotFoundException;
import com.glaze.flow.repositories.OTPRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final OTPRepository otpRepository;

    @Transactional
    public void activateAccount(Long userId, String token) {
        OTP otp = otpRepository.findByUserIdAndTokenAndType(userId, token, OTPType.ACCOUNT_ACTIVATION)
            .orElseThrow(() -> new ResourceNotFoundException("key"));

        boolean isTokenExpired = otp.getExpiresAt()
            .isBefore(LocalDateTime.now());

        if(isTokenExpired) {
            throw new ResourceExpiredException("key");
        }

        boolean areDifferentTokens = !otp.getToken().equals(token);
        if(areDifferentTokens) {
            throw new IllegalStateException("key");
        }

        otpRepository.delete(otp);
        otp.getUser()
            .getDetails()
            .setActive(true);
    }

}
