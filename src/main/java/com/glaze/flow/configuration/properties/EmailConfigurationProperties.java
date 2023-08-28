package com.glaze.flow.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.mail")
public record EmailConfigurationProperties(
    String username,
    String password,
    String protocol,
    String host,
    Integer port
){}
