package com.gameshop.www.eCommerce.api.model;

import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private boolean success;
    private String failureReason;

}
