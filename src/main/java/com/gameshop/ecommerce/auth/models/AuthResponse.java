package com.gameshop.ecommerce.auth.models;

import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private boolean success;
    private String failureReason;

}
