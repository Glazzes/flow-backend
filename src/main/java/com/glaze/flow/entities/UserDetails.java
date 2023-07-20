package com.glaze.flow.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "user_details")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDetails {

    @Id
    @Column(name = "user_id", updatable = false)
    private Long id;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "is_non_locked", nullable = false)
    private boolean isNonLocked;

    @Column(name = "has_not_expired", nullable = false)
    private boolean hasNotExpired;

    @Column(name = "are_credentials_not_expired", nullable = false)
    private boolean areCredentialsNonExpired;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private User user;

    public static UserDetails getRegistrationInstance() {
        return new UserDetails(null, false, true, true, true, null);
    }

    @Override
    public String toString() {
        return "UserDetails{" +
            "id=" + id +
            ", is_active=" + isActive +
            ", isNonLocked=" + isNonLocked +
            ", hasNotExpired=" + hasNotExpired +
            ", areCredentialsNonExpired=" + areCredentialsNonExpired +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        UserDetails details = (UserDetails) o;
        return Objects.equals(this.id, details.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
