package com.glaze.flow.services;

import com.glaze.flow.entities.AccountVerificationToken;
import com.glaze.flow.entities.User;
import com.glaze.flow.exceptions.ExpiredResourceException;
import com.glaze.flow.exceptions.ResourceNotFound;
import com.glaze.flow.repositories.AccountVerificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AccountVerificationService {
    private final AccountVerificationTokenRepository accountVerificationTokenRepository;

    public Long issueVerificationTokenForUser(User user) {
        AccountVerificationToken token = new AccountVerificationToken();
        token.setUser(user);
        token.setExpiresAt(LocalDateTime.now().plus(1, ChronoUnit.DAYS));

        AccountVerificationToken savedToken = accountVerificationTokenRepository.save(token);
        return savedToken.getId();
    }

    @Transactional
    public void activateAccount(Long id) {
        AccountVerificationToken token = accountVerificationTokenRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFound("key"));

        LocalDateTime expirationTimestamp = token.getExpiresAt();
        LocalDateTime now = LocalDateTime.now();
        boolean hasTokenExpired = now.isAfter(expirationTimestamp);
        if(hasTokenExpired) {
            throw new ExpiredResourceException("key");
        }

        User user = token.getUser();
        user.getDetails().setActive(true);
    }

}
