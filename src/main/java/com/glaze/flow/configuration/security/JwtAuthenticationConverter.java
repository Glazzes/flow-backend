package com.glaze.flow.configuration.security;

import java.util.List;
import java.util.stream.Collectors;

import com.glaze.flow.entities.User;
import com.glaze.flow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private static final String AUTHORITIES_CLAIM = "authorities";
    private final UserRepository userRepository;

    @Autowired
    public JwtAuthenticationConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        String usernameOrEmail = source.getSubject();
        User authenticatedUser = userRepository.findByUsernameOrEmail(usernameOrEmail)
            .orElseThrow(() -> new UsernameNotFoundException(""));

        List<SimpleGrantedAuthority> authorities = source.getClaimAsStringList(AUTHORITIES_CLAIM)
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        return new UserAuthenticationToken(authenticatedUser, source, authorities);
    }
}
