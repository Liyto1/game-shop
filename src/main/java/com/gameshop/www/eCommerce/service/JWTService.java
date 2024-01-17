package com.gameshop.www.eCommerce.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gameshop.www.eCommerce.model.LocalUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    private static final String USER_EMAIL = "email";
    private static final String USER_ID = "id";
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expire.time}")
    private long expireTime;
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
                .withClaim(USER_EMAIL, user.getEmail())
                .withIssuer(issuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + (expireTime * 1000)))
                .sign(algorithm);
    }
}
