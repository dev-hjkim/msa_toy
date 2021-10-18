package com.fashion.celebrity.auth.common.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        // 401
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
    }

    @ExceptionHandler(value= {AccessDeniedException.class})
    public void commence(HttpServletResponse httpServletResponse,
                         AccessDeniedException accessDeniedException) throws IOException {
        logger.error("403");
        httpServletResponse.sendError(httpServletResponse.SC_FORBIDDEN, "Authorization Failed : " + accessDeniedException.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public void commence(HttpServletResponse httpServletResponse,
                         Exception exception) throws IOException {
        logger.error("500");
        httpServletResponse.sendError(httpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error: " + exception.getMessage());
    }
}
