package com.gameshop.www.eCommerce.order.purchase;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductPurchase {
    private UUID id;
    private Integer totalPurchased;
}
