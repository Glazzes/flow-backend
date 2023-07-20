package com.glaze.flow.services;

import com.glaze.flow.entities.AccountVerificationToken;
import com.glaze.flow.entities.User;
import com.glaze.flow.events.UserRegistrationEvent;
import com.glaze.flow.exceptions.ExpiredResourceException;
import com.glaze.flow.exceptions.ResourceNotFound;
import com.glaze.flow.repositories.AccountVerificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AccountVerificationService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AccountVerificationService.class);

    private final AccountVerificationTokenRepository accountVerificationTokenRepository;

    @EventListener
    public void issueVerificationTokenForUser(UserRegistrationEvent event) {
        LOGGER.info("Handling user registration event with event {}", event);

        AccountVerificationToken token = new AccountVerificationToken();
        token.setUser(event.user());
        token.setExpiresAt(LocalDateTime.now().plus(1, ChronoUnit.DAYS));

        LOGGER.info("Saving user verification token for user with id {}", event.user().getId());
        accountVerificationTokenRepository.save(token);
    }

    @Transactional
    public void activateAccount(Long id) {
        LOGGER.info("Activating user account with id {} in service", id);
        AccountVerificationToken token = accountVerificationTokenRepository.findById(id)
            .orElseThrow(() -> {
                LOGGER.warn("Account verification token for id {} was not found", id);
                return new ResourceNotFound("key");
            });

        LocalDateTime expirationTimestamp = token.getExpiresAt();
        LocalDateTime now = LocalDateTime.now();

        boolean hasTokenExpired = now.isAfter(expirationTimestamp);
        if(hasTokenExpired) {
            throw new ExpiredResourceException("key");
        }

        LOGGER.info("Account with id {} has been activated successfully", id);
        User user = token.getUser();
        user.getDetails().setActive(true);
    }

}
