package com.glaze.flow.services;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;

import com.glaze.flow.constants.LocalizationConstants;
import com.glaze.flow.entities.Otp;
import com.glaze.flow.enums.OTPType;
import com.glaze.flow.exceptions.ResourceExpiredException;
import com.glaze.flow.exceptions.ResourceNotFoundException;
import com.glaze.flow.repositories.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final OtpRepository otpRepository;

    @Transactional
    public void activateAccount(Long userId, String token) {
        Otp otp = otpRepository.findByIdAndTokenAndType(userId, token, OTPType.ACCOUNT_ACTIVATION)
            .orElseThrow(() -> new ResourceNotFoundException(LocalizationConstants.RESOURCE_NOT_FOUND, userId));

        boolean isThisTokenExpired = otp.getExpiresAt()
            .isBefore(LocalDateTime.now());

        if(isThisTokenExpired) {
            throw new ResourceExpiredException(LocalizationConstants.RESOURCE_EXPIRED);
        }

        boolean areTokensDifferent = !otp.getToken().equals(token);
        if(areTokensDifferent) {
            throw new IllegalStateException("key");
        }

        otpRepository.delete(otp);
        otp.getUser()
            .getDetails()
            .setActive(true);
    }

}
