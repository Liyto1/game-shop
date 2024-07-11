package com.gameshop.www.eCommerce.cart.model.dto;

import com.gameshop.www.eCommerce.cart.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link Cart}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto implements Serializable {
   private Set<CartItemDto> cartItems;
   private UUID id;
}