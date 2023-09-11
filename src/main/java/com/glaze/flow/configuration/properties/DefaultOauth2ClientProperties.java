package com.glaze.flow.configuration.properties;

import jakarta.validation.constraints.NotNull;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.oauth2-client")
public record DefaultOauth2ClientProperties(
    @NotNull
    String id,

    @NotNull
    String clientId,

    @NotNull
    String clientSecret,

    @NotNull
    List<String> scopes,

    @NotNull
    List<String> redirectUris
) {}
