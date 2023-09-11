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

@Entity
@Table(name = "client")
@Getter
@Setter
public class Oauth2Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "client_secret_expires_at")
    private Instant clientSecretExpiresAt;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_id_issued_at")
    private Instant clientIdIssuedAt;

    @Column(length = 1000, name = "client_authentication_methods")
    private String clientAuthenticationMethods;

    @Column(length = 1000, name = "authorization_grant_types")
    private String authorizationGrantTypes;

    @Column(length = 1000, name = "redirect_uris")
    private String redirectUris;

    @Column(length = 1000, name = "post_logout_redirect_uris")
    private String postLogoutRedirectUris;

    @Column(length = 1000, name = "scopes")
    private String scopes;

    @Column(length = 2000, name = "client_settings")
    private String clientSettings;

    @Column(length = 2000, name = "token_settings")
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
