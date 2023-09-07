package com.glaze.flow.configuration.security;

import java.util.Collection;

import com.glaze.flow.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticatedUserDetails implements UserDetails {
    private final User authenticatedUser;
    public AuthenticatedUserDetails(User user) {
        this.authenticatedUser = user;
    }

    public User getAuthenticatedUser() {
        return authenticatedUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return authenticatedUser.getPassword();
    }

    @Override
    public String getUsername() {
        return authenticatedUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return authenticatedUser.getDetails()
            .isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return authenticatedUser.getDetails()
            .isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return authenticatedUser.getDetails()
            .isCredentialsNotExpired();
    }

    @Override
    public boolean isEnabled() {
        return authenticatedUser.getDetails()
            .isEnabled();
    }
}
