package com.gameshop.www.eCommerce.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    private static final String VERIFICATION_USER_EMAIL_KEY = "VERIFIC_EMAIL";
    private static final String RESET_PASSWORD_EMAIL_KEY = "RESET_EMAIL";
    private static final String USER_ID = "id";
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expire.time}")
    private long expireTime;
    @Value("${jwt.reset.expire.time}")
    private long resetExpire;
    private Algorithm algorithm;

    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC512(algorithmKey);
    }

    public String generateJWT(LocalUser user) {
        return JWT.create()
                .withClaim(USER_ID, user.getId().toString())
                .withIssuer(issuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + (expireTime * 1000)))
                .sign(algorithm);
    }

    public String getUser(String token) {
        return JWT.decode(token).getClaim(USER_ID).asString();
    }

    public String generateVerificationJWT(LocalUser user) {
        return JWT.create()
                .withClaim(VERIFICATION_USER_EMAIL_KEY, user.getEmail())
                .withIssuer(issuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + (expireTime * 1000)))
                .sign(algorithm);
    }

    public String generatePasswordResetJWT(LocalUser user) {
        return JWT.create()
                .withClaim(RESET_PASSWORD_EMAIL_KEY, user.getEmail())
                .withIssuer(issuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + (resetExpire * 1000)))
                .sign(algorithm);
    }
    public String getResetPasswordEmail(String token) {
        return JWT.require(algorithm).withIssuer(issuer).build()
                .verify(token).getClaim(RESET_PASSWORD_EMAIL_KEY).asString();
    }
}
