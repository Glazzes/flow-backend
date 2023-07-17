package com.glaze.flow.services;

import com.glaze.flow.dtos.request.SignUpRequest;
import com.glaze.flow.entities.User;
import com.glaze.flow.mappers.UserMapper;
import com.glaze.flow.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AccountVerificationService accountVerificationService;
    private final EmailService emailService;

    public Long save(SignUpRequest request) {
        User user = userMapper.signUpRequestToUser(request);
        User savedUser = userRepository.save(user);

        Long issuedVerificationToken = accountVerificationService.issueVerificationTokenForUser(savedUser);
        if(issuedVerificationToken != null) {
            emailService.sendAccountVerificationEmail(savedUser.getEmail(), savedUser.getId());
        }

        return savedUser.getId();
    }

}
