package com.glaze.flow.configuration.security;

import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

import com.glaze.flow.configuration.properties.KeyStoreConfigurationProperties;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class AuthorizationServerConfiguration {
    private static final String LOGIN_FORM_URL = "/login";
    private static final String KEY_STORE_FILE_LOCATION = "authentication/keystore.p12";
    private final KeyStoreConfigurationProperties keyStoreConfigurationProperties;

    @Autowired
    public AuthorizationServerConfiguration(KeyStoreConfigurationProperties properties) {
        this.keyStoreConfigurationProperties = properties;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);
        httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
            .oidc(Customizer.withDefaults());

        httpSecurity
            .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(
            new LoginUrlAuthenticationEntryPoint(LOGIN_FORM_URL)
        ));

        return httpSecurity.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain formLoginSecurityfilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .securityMatcher(LOGIN_FORM_URL)
            .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
            .formLogin(form -> form.loginPage(LOGIN_FORM_URL))
            .build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("flow")
            .clientSecret("bcrypt encoded secret")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .clientSettings(
                ClientSettings.builder()
                    .requireProofKey(true)
                    .requireAuthorizationConsent(false)
                    .build()
            )
            .tokenSettings(
                TokenSettings.builder()
                    .accessTokenTimeToLive(Duration.ofMinutes(15L))
                    .refreshTokenTimeToLive(Duration.ofDays(15L))
                    .authorizationCodeTimeToLive(Duration.ofMinutes(5L))
                    .reuseRefreshTokens(true)
                    .build()
            )
            .authorizationGrantTypes(grants -> {
                grants.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                grants.add(AuthorizationGrantType.REFRESH_TOKEN);
            })
            .scopes(scopes -> {
                scopes.add(OidcScopes.EMAIL);
                scopes.add(OidcScopes.PROFILE);
            })
            .redirectUri("https://google.com")
            .build();

        return new InMemoryRegisteredClientRepository(client);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
            .build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws Exception {
        KeyPair keyPair = this.getKeyPairFromKeyStore();
        RSAKey key = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
            .privateKey(keyPair.getPrivate())
            .keyID(UUID.randomUUID().toString())
            .build();

        return new ImmutableJWKSet<>(new JWKSet(key));
    }

    private KeyPair getKeyPairFromKeyStore() throws Exception {
        ClassPathResource keyStoreFile = new ClassPathResource(KEY_STORE_FILE_LOCATION);
        String alias = keyStoreConfigurationProperties.keyAlias();
        char[] password = keyStoreConfigurationProperties.keyStorePassword()
            .toCharArray();

        KeyStore keyStore = KeyStore.getInstance(keyStoreConfigurationProperties.keyStoreType());
        keyStore.load(keyStoreFile.getInputStream(), password);

        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password);
        PublicKey publicKey = keyStore.getCertificate(alias)
            .getPublicKey();

        boolean isNotAnRsaPrivateKey = !(privateKey instanceof RSAPrivateKey);
        boolean isNotAnRsaPublicKey = !(publicKey instanceof RSAPublicKey);
        if(isNotAnRsaPrivateKey || isNotAnRsaPublicKey) {
            throw new IllegalStateException("The loaded keys are not RSA keys");
        }

        return new KeyPair(publicKey, privateKey);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

}
