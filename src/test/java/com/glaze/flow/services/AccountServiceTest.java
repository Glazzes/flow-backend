package com.glaze.flow.services;

import java.time.LocalDateTime;
import java.util.Optional;

import com.github.javafaker.Faker;
import com.glaze.flow.entities.Otp;
import com.glaze.flow.entities.User;
import com.glaze.flow.entities.UserDetails;
import com.glaze.flow.enums.OTPType;
import com.glaze.flow.exceptions.ResourceExpiredException;
import com.glaze.flow.exceptions.ResourceNotFoundException;
import com.glaze.flow.repositories.OtpRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AccountService Tests")
class AccountServiceTest {
    private AccountService underTest;
    private Faker faker;
    @Mock private OtpRepository otpRepository;
    @Captor private ArgumentCaptor<Otp> argumentCaptor;

    @BeforeEach
    void setUp() {
        underTest = new AccountService(otpRepository);
        faker = new Faker();
    }

    @Test
    @DisplayName("activateAccount when otp exists and is valid delete otp")
    void itShouldActivateUserAccount() {
        // Given
        Long tokenId = faker.number().randomNumber();
        String token = faker.internet().uuid();
        OTPType type = OTPType.ACCOUNT_ACTIVATION;

        // When
        UserDetails userDetails = spy(UserDetails.getSignUpInstance());

        String username = faker.name().username();
        User user = spy(User.builder()
            .id(faker.number().randomNumber())
            .username(username)
            .displayUsername(username)
            .email(faker.internet().emailAddress())
            .details(userDetails)
            .password(faker.internet().password())
            .profilePicture(faker.avatar().image())
            .build()
        );

        Otp otp = spy(Otp.builder()
            .token(token)
            .type(OTPType.ACCOUNT_ACTIVATION)
            .expiresAt(LocalDateTime.now().plusHours(1L))
            .user(user)
            .build()
        );

        Optional<Otp> otpOptional = Optional.ofNullable(otp);
        when(otpRepository.findByIdAndTokenAndType(tokenId, token, type))
            .thenReturn(otpOptional);

        // Then
        underTest.activateAccount(tokenId, token);
        verify(otpRepository).delete(argumentCaptor.capture());
        assertThat(otp).isEqualTo(argumentCaptor.getValue());

        verify(otp).getUser();
        verify(user).getDetails();
        verify(userDetails).setActive(true);
    }

    @Test
    @DisplayName("activateAccount when otp does not exists throw ResourceNotFoundException")
    void activateAccountShouldThrowResourceNotFoundException() {
        // Given
        Long tokenId = faker.number().randomNumber();
        String token = faker.internet().uuid();
        OTPType type = OTPType.ACCOUNT_ACTIVATION;

        // When
        Optional<Otp> empty = Optional.empty();
        when(otpRepository.findByIdAndTokenAndType(tokenId, token, type))
            .thenReturn(empty);

        // Then
        assertThatThrownBy(() -> underTest.activateAccount(tokenId, token))
            .isInstanceOf(ResourceNotFoundException.class);

        verify(otpRepository, never()).delete(any(Otp.class));
    }

    @Test
    @DisplayName("activateAccount when otp tokens are different throw IllegalStateException")
    void activateAccountShouldThrowIllegalStateException() {
        // Given
        Long tokenId = faker.number().randomNumber();
        String token = faker.internet().uuid();
        OTPType type = OTPType.ACCOUNT_ACTIVATION;

        // When
        String differentToken = faker.internet().uuid();
        Otp otp = Otp.builder()
            .token(differentToken)
            .type(OTPType.ACCOUNT_ACTIVATION)
            .expiresAt(LocalDateTime.now().plusHours(5L))
            .build();

        Optional<Otp> optionalOtp = Optional.ofNullable(otp);
        when(otpRepository.findByIdAndTokenAndType(tokenId, token, type))
            .thenReturn(optionalOtp);

        // Then
        assertThatThrownBy(() -> underTest.activateAccount(tokenId, token))
            .isInstanceOf(IllegalStateException.class);

        verify(otpRepository, never()).delete(any(Otp.class));
    }

    @Test
    @DisplayName("activateAccount when otp is expired throw ResourceExpiredException")
    void activateAccountShouldThrowResourceExpiredException() {
        // Given
        Long tokenId = faker.number().randomNumber();
        String token = faker.internet().uuid();
        OTPType type = OTPType.ACCOUNT_ACTIVATION;

        // When
        String username = faker.name().username();
        User user = User.builder()
            .id(faker.number().randomNumber())
            .username(username)
            .displayUsername(username)
            .email(faker.internet().emailAddress())
            .details(UserDetails.getSignUpInstance())
            .password(faker.internet().password())
            .profilePicture(faker.avatar().image())
            .build();

        Otp otp = Otp.builder()
            .token(token)
            .type(OTPType.ACCOUNT_ACTIVATION)
            .expiresAt(LocalDateTime.now().minusMinutes(5L))
            .user(user)
            .build();

        Optional<Otp> empty = Optional.ofNullable(otp);
        when(otpRepository.findByIdAndTokenAndType(tokenId, token, type))
            .thenReturn(empty);

        // Then
        assertThatThrownBy(() -> underTest.activateAccount(tokenId, token))
            .isInstanceOf(ResourceExpiredException.class);

        verify(otpRepository, never()).delete(any(Otp.class));
    }
}
