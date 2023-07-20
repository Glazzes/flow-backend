package com.glaze.flow.services;

import com.glaze.flow.dtos.request.SignUpRequest;
import com.glaze.flow.entities.User;
import com.glaze.flow.entities.UserDetails;
import com.glaze.flow.events.UserRegistrationEvent;
import com.glaze.flow.mappers.UserMapper;
import com.glaze.flow.repositories.UserDetailsRepository;
import com.glaze.flow.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ApplicationEventPublisher eventPublisher;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public Long save(SignUpRequest request) {
        LOGGER.info("Saving new user with request {}", request);
        User user = userMapper.signUpRequestToUser(request);
        User savedUser = userRepository.save(user);

        UserRegistrationEvent event = new UserRegistrationEvent(savedUser);
        LOGGER.info("Dispatching user registration event to listeners");
        eventPublisher.publishEvent(event); // Handled by EmailService and AccountVerificationService

        LOGGER.info("Saved user successfully with result {}", savedUser);
        return savedUser.getId();
    }

}
