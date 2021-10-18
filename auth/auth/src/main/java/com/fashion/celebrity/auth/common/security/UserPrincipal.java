package com.fashion.celebrity.auth.common.security;

import com.fashion.celebrity.auth.dto.UserDto;
import com.fashion.celebrity.auth.dto.request.ReqLoginDto;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class UserPrincipal implements UserDetails {
    private static final Logger logger = LoggerFactory.getLogger(UserPrincipal.class);

    private String username;
    private String password;

    public UserPrincipal(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static UserPrincipal create(UserDto user) {
        return new UserPrincipal(user.getUsername(), user.getPassword());
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}