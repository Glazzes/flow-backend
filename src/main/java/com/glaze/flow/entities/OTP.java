package com.glaze.flow.entities;

import com.glaze.flow.enums.OTPType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OTP {

    @Id
    @SequenceGenerator(
        name = "otp_sequence_generator",
        sequenceName = "otp_sequence",
        allocationSize = 10
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "otp_sequence_generator"
    )
    private Long id;

    @Column(name = "token", nullable = false, updatable = false)
    private String token;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "type", nullable = false, updatable = false)
    private OTPType type;

    @Column(name = "expires_at", nullable = false, updatable = false)
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
