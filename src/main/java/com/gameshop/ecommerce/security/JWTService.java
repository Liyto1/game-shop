package com.gameshop.ecommerce.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gameshop.ecommerce.user.store.LocalUserEntity;
import com.gameshop.ecommerce.utils.JwtProps;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.gameshop.ecommerce.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class JWTService {

    private final JwtProps jwtProps;
    private Algorithm algorithm;

    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC512(jwtProps.getKey());
    }

    //TODO: Зробити заміну на MyUserDetail з Security

    public String generateJWT(LocalUserEntity user) {
        return JWT.create()
                .withClaim(USER_ID, user.getId().toString())
                .withIssuer(jwtProps.getIssuer())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProps.getAccessToken()))
                .sign(algorithm);
    }

    public String getUser(String token) {
        return JWT.decode(token).getClaim(USER_ID).asString();
    }

    public String generateVerificationJWT(LocalUserEntity user) {
        return JWT.create()
                .withClaim(VERIFICATION_USER_EMAIL_KEY, user.getEmail())
                .withIssuer(jwtProps.getIssuer())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProps.getAccessToken()))
                .sign(algorithm);
    }

    public String generatePasswordResetJWT(LocalUserEntity user) {
        return JWT.create()
                .withClaim(RESET_PASSWORD_EMAIL_KEY, user.getEmail())
                .withIssuer(jwtProps.getIssuer())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProps.getRefreshToken()))
                .sign(algorithm);
    }

    public String getResetPasswordEmail(String token) {
        return JWT.require(algorithm).withIssuer(jwtProps.getIssuer()).build()
                .verify(token).getClaim(RESET_PASSWORD_EMAIL_KEY).asString();
    }
}
