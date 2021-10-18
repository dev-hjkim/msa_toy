package com.fashion.celebrity.auth.common.security;

import com.fashion.celebrity.auth.common.utils.AES256Util;
import io.jsonwebtoken.*;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication, String type) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate;
        if (type.equals("ACCESS")) {
            expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        } else {
            expiryDate = new Date(now.getTime() + jwtExpirationInMs * 7);
        }

        JSONObject jobj = new JSONObject();

        jobj.put("username", userPrincipal.getUsername());

        String encStr = "";
        try {
            encStr = AES256Util.encrypt(jobj.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Jwts.builder().setSubject(encStr).setIssuedAt(new Date())
                .setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch(MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch(ExpiredJwtException ex) {
            logger.error("Invalid JWT token(expired)");
        } catch(UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch(IllegalArgumentException ex) {
            logger.error("JWT claims string is empty");
        }
        return false;
    }

    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }
}

