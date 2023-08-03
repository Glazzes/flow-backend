package com.glaze.flow.entities;

import com.glaze.flow.enums.OTPType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "otp")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Otp {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Otp{" +
            "id=" + id +
            ", type=" + type +
            ", expiresAt=" + expiresAt +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Otp otp = (Otp) o;
        return Objects.equals(id, otp.id) && Objects.equals(token, otp.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
