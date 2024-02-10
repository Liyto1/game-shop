package com.gameshop.www.eCommerce.auth.model;

import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private boolean success;
    private String failureReason;

}
