package com.glaze.flow.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "AVT")
@Table(name = "account_verification_token")
@Getter
@Setter
@NoArgsConstructor
public class AccountVerificationToken {

    @Id
    @Column(name = "user_id", updatable = false)
    private Long id;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public String toString() {
        return "AccountVerificationToken{" +
            "id=" + id +
            ", expiresAt=" + expiresAt +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        AccountVerificationToken that = (AccountVerificationToken) o;

        return Objects.equals(that.id, this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
