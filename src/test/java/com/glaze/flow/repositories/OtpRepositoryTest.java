package com.glaze.flow.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import com.github.javafaker.Faker;
import com.glaze.flow.entities.Otp;
import com.glaze.flow.entities.User;
import com.glaze.flow.enums.OTPType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("OtpRepository Tests")
class OtpRepositoryTest {

    private final Faker faker = new Faker();

    @Autowired
    private OtpRepository underTest;

    @Autowired
    private UserRepository userRepository;

    @Test
    void itShouldFindOtp() {
        // Given
        String username = faker.name().username();
        User user = User.builder()
            .id(1L)
            .email(faker.internet().emailAddress())
            .username(username)
            .displayUsername(username)
            .password(faker.internet().password())
            .profilePicture(null)
            .build();

        String token = faker.internet().uuid();
        Otp otp = Otp.builder()
            .id(1L)
            .user(user)
            .token(token)
            .type(OTPType.ACCOUNT_ACTIVATION)
            .expiresAt(LocalDateTime.now())
            .build();

        // When
        userRepository.save(user);
        underTest.save(otp);

        // Then
        Optional<Otp> result = underTest.findByIdAndTokenAndType(user.getId(), token, OTPType.ACCOUNT_ACTIVATION);
        assertThat(result).isPresent()
            .hasValueSatisfying(o -> assertThat(o).isEqualTo(otp));
    }

}
