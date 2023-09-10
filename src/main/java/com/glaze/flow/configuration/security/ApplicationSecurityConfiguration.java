package com.glaze.flow.configuration.security;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration {
    private final JwtDecoder jwtDecoder;
    private final JwtAuthenticationConverter customJwtConverter;

    @Autowired
    public ApplicationSecurityConfiguration(
        JwtDecoder jwtDecoder,
        JwtAuthenticationConverter customJwtConverter
    ) {
        this.jwtDecoder = jwtDecoder;
        this.customJwtConverter = customJwtConverter;
    }

    @Bean
    @Order(3)
    public SecurityFilterChain applicationSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(CsrfConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login").permitAll()
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/v1/users/account-registration"
                ).permitAll()
                .requestMatchers(
                    HttpMethod.GET,
                    "/css/**",
                    "/images/**",
                    "/fonts/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(customizer -> customizer.jwt(
                jwt -> jwt.decoder(jwtDecoder)
                    .jwtAuthenticationConverter(customJwtConverter)
            ));

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

}
