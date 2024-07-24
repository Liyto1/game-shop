package com.gameshop.ecommerce.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProps {
    private String key;
    private String issuer;
    private long accessToken;
    private long refreshToken;
}