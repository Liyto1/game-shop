package com.gameshop.ecommerce.cart.store.dto;

import com.gameshop.ecommerce.cart.store.CartEntity;
import lombok.Builder;
import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link CartEntity}
 */
@Builder
public record CartDto(Set<CartItemDto> cartItems, Long id) implements Serializable { }