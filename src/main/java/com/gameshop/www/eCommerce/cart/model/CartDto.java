package com.gameshop.www.eCommerce.cart.model;

import lombok.Data;

import java.util.UUID;

@Data
public class CartDto {

    private UUID id;
    private Integer quantity;

}
