package com.gameshop.www.eCommerce.cart.model.dto;

import com.gameshop.www.eCommerce.cart.model.Cart;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link Cart}
 */
@Data
public class CartDto implements Serializable {
   private Set<CartItemDto> cartItems;
   private UUID id;
}