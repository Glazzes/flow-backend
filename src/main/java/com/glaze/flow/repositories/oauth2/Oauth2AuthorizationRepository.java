package com.glaze.flow.repositories.oauth2;

import java.util.Optional;

import com.glaze.flow.entities.oauth2.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@SuppressWarnings("all")
public interface Oauth2AuthorizationRepository extends JpaRepository<Authorization, String> {
    Optional<Authorization> findByState(String state);
    Optional<Authorization> findByAuthorizationCodeValue(String authorizationCode);
    Optional<Authorization> findByAccessTokenValue(String accessToken);
    Optional<Authorization> findByRefreshTokenValue(String refreshToken);
    Optional<Authorization> findByOidcIdTokenValue(String idToken);
    Optional<Authorization> findByUserCodeValue(String userCode);
    Optional<Authorization> findByDeviceCodeValue(String deviceCode);
    @Query("select a from Authorization a where a.state = :token" +
        " or a.authorizationCodeValue = :token" +
        " or a.accessTokenValue = :token" +
        " or a.refreshTokenValue = :token" +
        " or a.oidcIdTokenValue = :token" +
        " or a.userCodeValue = :token" +
        " or a.deviceCodeValue = :token"
    )
    Optional<Authorization> findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrOidcIdTokenValueOrUserCodeValueOrDeviceCodeValue(@Param("token") String token);
}
