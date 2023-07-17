package com.glaze.flow.services;

import com.glaze.flow.entities.AccountVerificationToken;
import com.glaze.flow.entities.User;
import com.glaze.flow.repositories.AccountVerificationTokenRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AccountVerificationService {
    private AccountVerificationTokenRepository accountVerificationTokenRepository;

    public Long issueVerificationTokenForUser(User user) {
        AccountVerificationToken token = new AccountVerificationToken();
        token.setUser(user);
        token.setExpiresAt(LocalDateTime.now().plus(1, ChronoUnit.DAYS));

        AccountVerificationToken savedToken = accountVerificationTokenRepository.save(token);
        return savedToken.getId();
    }

}
