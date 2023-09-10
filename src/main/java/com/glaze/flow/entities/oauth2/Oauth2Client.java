package com.glaze.flow.entities.oauth2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "client")
@Getter
@Setter
public class Oauth2Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String clientId;
    private String clientSecret;
    private Instant clientSecretExpiresAt;
    private String clientName;

    @CreatedDate
    private Instant clientIdIssuedAt;

    @Column(length = 1000)
    private String clientAuthenticationMethods;

    @Column(length = 1000)
    private String authorizationGrantTypes;

    @Column(length = 1000)
    private String redirectUris;

    @Column(length = 1000)
    private String postLogoutRedirectUris;

    @Column(length = 1000)
    private String scopes;

    @Column(length = 2000)
    private String clientSettings;

    @Column(length = 2000)
    private String tokenSettings;

    @Override
    public String toString() {
        return "Oauth2Client {" +
            "id=" + id +
            ", clientId=" + clientId +
            ", clientSecret=" + clientSecret;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        }

        if(other == null || this.getClass() != other.getClass()) {
            return false;
        }

        Oauth2Client otherClient = (Oauth2Client) other;
        return this.id.equals(otherClient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, clientSecret);
    }
}
