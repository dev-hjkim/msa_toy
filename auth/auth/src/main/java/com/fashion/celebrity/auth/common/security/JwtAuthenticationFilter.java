package com.fashion.celebrity.auth.common.security;

import com.fashion.celebrity.auth.common.utils.AES256Util;
import com.fashion.celebrity.auth.dto.UserDto;
import lombok.SneakyThrows;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    public JwtTokenProvider tokenProvider;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            String jwt = getJwtFromRequest(request);

            // token이 있고 유효한 경우
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                String claims = tokenProvider.getUserIdFromJWT(jwt);
                String deStr = "";
                try {
                    deStr = AES256Util.decrypt(claims);
                } catch (Exception ex) {
                    logger.error("decrypt ex {} {}", claims, ex.getMessage());
                }

                JSONParser jp = new JSONParser();
                JSONObject jobj = (JSONObject)jp.parse(deStr);

                UserDto user = new UserDto();
                user.setUsername(jobj.get("username").toString());

                UserDetails userDetails = UserPrincipal.create(user);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else if (StringUtils.hasText(jwt) && !tokenProvider.validateToken(jwt)) { // token이 있으나 유효하지 않은 경우
                throw new AccessDeniedException("Invalid Token!");
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
            throw new Exception();
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
