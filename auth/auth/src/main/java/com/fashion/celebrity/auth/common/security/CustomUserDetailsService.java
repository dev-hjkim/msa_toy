package com.fashion.celebrity.auth.common.security;

import com.fashion.celebrity.auth.dto.UserDto;
import com.fashion.celebrity.auth.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserDto user = this.authService.selectAuthUser(username);
            return UserPrincipal.create(user);
        } catch(Exception ex) {
            logger.error("loadUserByUsername ex {}", ex.toString());
        }
        return null;
    }
}
