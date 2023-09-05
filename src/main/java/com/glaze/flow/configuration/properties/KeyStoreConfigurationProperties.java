package com.glaze.flow.configuration.properties;

import jakarta.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.keystore")
public record KeyStoreConfigurationProperties(
    @NotNull
    String keyStoreType,

    @NotNull
    String keyStorePassword,

    @NotNull
    String keyAlias
) {}
