package com.marcincho.service_msg.utils;

import java.util.Date;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.marcincho.service_msg.config.UserDetailsImpl;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTUtils {

    private final String appName;
    private final Algorithm algorithm;
    // now I'm trying using Auth0 token.

    public JWTUtils(@Value("info.marcincho.secret") String jwtSecret, @Value("spring.application.name") String appName) {
        this.appName = appName;
        this.algorithm = Algorithm.HMAC512(jwtSecret);
    }



    public String generateJwtToken(Authentication authentication) throws IllegalArgumentException, JWTCreationException {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withClaim("email", userPrincipal.getEmail())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date((new Date()).getTime() + 7200000))
                .withIssuer(appName)
                .sign(algorithm);
    }

    public String validateJwtToken(String token) throws JWTVerificationException {
        try{
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(appName)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException e) {
            log.error("JWT Token Couldn't validate {} " , e);
            return "";
        }


    }





//    public String generateJwtToken(Authentication authentication) {
//
//        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
//        //    @Value("info.marcincho.expiration")
//        long jwtExpirationMs = 7200000L;
//        String token = Jwts.builder()
//                .subject(userPrincipal.getUsername())
//                .issuedAt(new Date())
//                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
//                .signWith(key())
//                .compact();
//        return token;
//
//    }
//
//    private SecretKey key() {
////        return Jwts.SIG.HS512.key().build();
//        byte[] keyBytes = Jwts.SIG.HS512.key().build().getEncoded();
//        return Keys.hmacShaKeyFor(keyBytes);
//
//    }
//
//    public String getUserNameFromToken(String token) {
//        return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload().getSubject();
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = getAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    public Claims getAllClaims(String token) {
//        return  parser()
//                .decryptWith(key())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
//
//    public boolean validateJwtToken(String authToken) {
//        try {
//            log.info("Here auth token : {}", authToken);
//            parser().verifyWith(key()).build().parse(authToken);
//            return true;
//        } catch (MalformedJwtException e) {
//            log.error("Invalid token : {}", e.getMessage());
//        } catch (ExpiredJwtException e) {
//            log.error("Token is expired : {}", e.getMessage());
//        } catch (UnsupportedJwtException | IllegalArgumentException | SecurityException e) {
//            log.error("Other exception -> : {}", e.getMessage());
//        }
//        return false;
//    }
//
//    public String parseJwt(StompHeaderAccessor accessor) {
//        String token = accessor.getFirstNativeHeader("Authorization");
//        String jwt = null;
//        if (token != null && token.length() > 7) {
//            log.info("The token looks like that : \n {}", token);
//            jwt = token.replace("Bearer ", "");
//        }
//        return jwt;
//    }

}
