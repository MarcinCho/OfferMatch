package com.marcincho.service_msg.utils;

import com.marcincho.service_msg.config.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parser;

@Slf4j
@Component
public class JWTUtils {

    @Value("info.marcincho.secret")
    private String jwtSecret;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        //    @Value("info.marcincho.expiration")
        Long jwtExpirationMs = 7200000L;
        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();

    }

    private SecretKey key() {
        return Jwts.SIG.HS512.key().build();

    }

    public String getUserNameFromJwtToken(String token) {
        return parser().verifyWith(key()).build().parseUnsecuredClaims(token).getPayload().getSubject();
    }

    public boolean validateJwtToken(String authToken){
        try{
            parser().verifyWith(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid token : {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Token is expired : {}", e.getMessage());
        } catch ( UnsupportedJwtException | IllegalArgumentException | SecurityException e ){
            log.error("Other exception -> : {}", e.getMessage());
        }
        return false;
    }

        public String parseJwt(StompHeaderAccessor accessor) {
            String token = accessor.getFirstNativeHeader("Authorization");
            String jwt = null;
            if (token != null && token.length() > 7) {
                log.info("The token looks like that : \n {}", token);
                jwt = token.substring(7);
            }
            return jwt;
        }



}
