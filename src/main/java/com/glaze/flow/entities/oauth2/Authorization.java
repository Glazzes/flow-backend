package com.glaze.flow.entities.oauth2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "oauth2_authorization")
@Getter
@Setter
public class Authorization {
    @Id
    @Column
    private String id;

    @Column(name = "registered_client_id")
    private String registeredClientId;

    @Column(name = "principal_name")
    private String principalName;

    @Column(name = "authorization_grant_type")
    private String authorizationGrantType;

    @Column(length = 1000, name = "authorized_scopes")
    private String authorizedScopes;

    @Column(length = 4000, name = "attributes")
    private String attributes;

    @Column(length = 500, name = "state")
    private String state;

    @Column(length = 4000, name = "authorization_code_value")
    private String authorizationCodeValue;

    @Column(name = "authorization_code_issued_at")
    private Instant authorizationCodeIssuedAt;

    @Column(name = "authorization_code_expires_at")
    private Instant authorizationCodeExpiresAt;

    @Column(name = "authorization_code_metadata")
    private String authorizationCodeMetadata;

    @Column(length = 4000, name = "access_token_value")
    private String accessTokenValue;

    @Column(name = "access_token_issued_at")
    private Instant accessTokenIssuedAt;

    @Column(name = "access_token_expires_at")
    private Instant accessTokenExpiresAt;

    @Column(length = 2000, name = "access_token_metadata")
    private String accessTokenMetadata;

    @Column(name = "access_token_type")
    private String accessTokenType;

    @Column(length = 1000, name = "access_token_scopes")
    private String accessTokenScopes;

    @Column(length = 4000, name = "refresh_token_value")
    private String refreshTokenValue;

    @Column(name = "refresh_token_issued_at")
    private Instant refreshTokenIssuedAt;

    @Column(name = "refresh_token_expires_at")
    private Instant refreshTokenExpiresAt;

    @Column(length = 2000, name = "refresh_token_metadata")
    private String refreshTokenMetadata;

    @Column(length = 4000, name = "oidc_id_token_value")
    private String oidcIdTokenValue;

    @Column(name = "oidc_id_token_issued_at")
    private Instant oidcIdTokenIssuedAt;

    @Column(name = "oidc_id_token_expires_at")
    private Instant oidcIdTokenExpiresAt;

    @Column(length = 2000, name = "oidc_id_token_metadata")
    private String oidcIdTokenMetadata;

    @Column(length = 2000, name = "oidc_id_token_claims")
    private String oidcIdTokenClaims;

    @Column(length = 4000, name = "user_code_value")
    private String userCodeValue;

    @Column(name = "user_code_issued_at")
    private Instant userCodeIssuedAt;

    @Column(name = "user_code_expires_at")
    private Instant userCodeExpiresAt;

    @Column(length = 2000, name = "user_code_metadata")
    private String userCodeMetadata;

    @Column(length = 4000, name = "device_code_value")
    private String deviceCodeValue;

    @Column(name = "device_code_issued_at")
    private Instant deviceCodeIssuedAt;

    @Column(name = "device_code_expires_at")
    private Instant deviceCodeExpiresAt;

    @Column(length = 2000, name = "device_code_metadata")
    private String deviceCodeMetadata;

    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        }

        if(other == null || this.getClass() != other.getClass()) {
            return false;
        }

        Authorization otherEntity = (Authorization) other;
        return this.id.equals(otherEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registeredClientId, principalName, authorizationGrantType);
    }
}
