package com.gameshop.www.eCommerce.cart.model;

import lombok.Data;

import java.util.UUID;

@Data
public class CartBody {

    private UUID id;
    private Integer quantity;

}
