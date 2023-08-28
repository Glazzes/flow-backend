package com.glaze.flow.services;

import com.glaze.flow.dtos.request.SignUpRequest;
import com.glaze.flow.entities.User;
import com.glaze.flow.entities.UserDetails;
import com.glaze.flow.events.SignUpEvent;
import com.glaze.flow.mappers.UserMapper;
import com.glaze.flow.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("User Service Unit Tests")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks private UserService underTest;
    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @Mock private ApplicationEventPublisher eventPublisher;

    @Test
    public void save() {
        // Given
        SignUpRequest request = new SignUpRequest("RandomUserName", "random@email.com", "password");

        // When
        UserDetails details = UserDetails.getSignUpInstance();
        User newUser = User.builder()
                .id(null)
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .displayUsername(request.username())
                .profilePicture(null)
                .details(details)
                .build();

        SignUpEvent event = new SignUpEvent(newUser);

        when(userMapper.signUpRequestToUser(request))
                .thenReturn(newUser);

        when(userRepository.save(newUser))
                .thenReturn(newUser);

        underTest.save(request);

        // Then
        verify(userMapper).signUpRequestToUser(request);
        verify(userRepository).save(newUser);
        verify(eventPublisher).publishEvent(event);
    }

}
