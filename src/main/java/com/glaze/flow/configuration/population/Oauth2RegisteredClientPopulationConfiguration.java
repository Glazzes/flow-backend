package com.glaze.flow.configuration.population;

import java.time.Duration;

import com.glaze.flow.configuration.properties.DefaultOauth2ClientProperties;
import com.glaze.flow.configuration.security.oauth2.JpaRegisteredClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

@Component
public class Oauth2RegisteredClientPopulationConfiguration {
    private final JpaRegisteredClientRepository registeredClientRepository;
    private final DefaultOauth2ClientProperties clientProperties;

    @Autowired
    public Oauth2RegisteredClientPopulationConfiguration(
        JpaRegisteredClientRepository registeredClientRepository,
        DefaultOauth2ClientProperties clientProperties
    ) {
        this.registeredClientRepository = registeredClientRepository;
        this.clientProperties = clientProperties;
    }

    public void populate() {
        boolean clientAlreadyExists = registeredClientRepository.existsById(clientProperties.id());
        if (clientAlreadyExists) {
            return;
        }

        RegisteredClient registeredClient = RegisteredClient.withId(clientProperties.id())
            .clientId(clientProperties.clientId())
            .clientSecret(clientProperties.clientSecret())
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantTypes(grants -> {
                grants.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                grants.add(AuthorizationGrantType.REFRESH_TOKEN);
            })
            .scopes(scopes -> {
                scopes.add(OidcScopes.EMAIL);
                scopes.add(OidcScopes.PROFILE);
                scopes.add(OidcScopes.OPENID);
                scopes.addAll(clientProperties.scopes());
            })
            .clientSettings(
                ClientSettings.builder()
                    .requireProofKey(true)
                    .requireAuthorizationConsent(false)
                    .build()
            )
            .tokenSettings(
                TokenSettings.builder()
                    .accessTokenTimeToLive(Duration.ofMinutes(20L))
                    .refreshTokenTimeToLive(Duration.ofDays(15L))
                    .authorizationCodeTimeToLive(Duration.ofMinutes(2L))
                    .build()
            )
            .redirectUris(uris -> uris.addAll(clientProperties.redirectUris()))
            .build();

        registeredClientRepository.save(registeredClient);
    }
}
