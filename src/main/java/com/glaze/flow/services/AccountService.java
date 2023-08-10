package com.glaze.flow.services;

import com.glaze.flow.constants.LocalizationConstants;
import com.glaze.flow.entities.Otp;
import com.glaze.flow.enums.OTPType;
import com.glaze.flow.exceptions.ResourceExpiredException;
import com.glaze.flow.exceptions.ResourceNotFoundException;
import com.glaze.flow.repositories.OtpRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final OtpRepository otpRepository;

    @Transactional
    public void activateAccount(Long userId, String token) {
        Otp otp = otpRepository.findByUserIdAndTokenAndType(userId, token, OTPType.ACCOUNT_ACTIVATION)
            .orElseThrow(() -> new ResourceNotFoundException(LocalizationConstants.RESOURCE_NOT_FOUND));

        boolean isTokenExpired = otp.getExpiresAt()
            .isBefore(LocalDateTime.now());

        if(isTokenExpired) {
            throw new ResourceExpiredException(LocalizationConstants.RESOURCE_EXPIRED);
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
