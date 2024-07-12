package com.gameshop.ecommerce.auth.model;

import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private boolean success;
    private String failureReason;

}
