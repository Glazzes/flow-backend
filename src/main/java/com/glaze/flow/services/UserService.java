package com.glaze.flow.services;

import jakarta.transaction.Transactional;

import com.glaze.flow.dtos.request.SignUpRequest;
import com.glaze.flow.entities.User;
import com.glaze.flow.events.SignUpEvent;
import com.glaze.flow.mappers.UserMapper;
import com.glaze.flow.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserService(
        UserMapper userMapper,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        ApplicationEventPublisher eventPublisher
    ) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Transactional
    public Long save(SignUpRequest request) {
        LOGGER.info("Saving new user with request {}", request);

        User user = userMapper.signUpRequestToUser(request);
        String encodedPassword = passwordEncoder.encode(request.password());
        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);

        SignUpEvent event = new SignUpEvent(savedUser);
        LOGGER.info("Dispatching signup event to all registered listeners");
        eventPublisher.publishEvent(event); // Handled by EmailService and AccountVerificationService

        LOGGER.info("Saved user successfully with result {}", savedUser);
        return savedUser.getId();
    }

}
