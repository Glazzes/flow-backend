package com.glaze.flow.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(name = "username_uq", columnNames = "username"),
        @UniqueConstraint(name = "email_uq", columnNames = "email")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @SequenceGenerator(
        name = "user_id_sequence_generator",
        sequenceName = "user_id_sequence",
        allocationSize = 10
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "user_id_sequence_generator"
    )
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", columnDefinition = "text not null")
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "display_username", nullable = false)
    private String displayUsername;

    @Column(name = "profile_picture", columnDefinition = "text")
    private String profilePicture;

    @OneToOne(mappedBy = "user")
    private UserDetails details;

    @OneToOne(mappedBy = "user")
    private AccountVerificationToken verificationToken;

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;

        return Objects.equals(this.id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }

}
