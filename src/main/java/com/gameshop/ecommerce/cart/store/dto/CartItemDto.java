package com.gameshop.ecommerce.cart.store.dto;

import com.gameshop.ecommerce.cart.store.CartItemEntity;
import com.gameshop.ecommerce.product.dto.ProductCatalogDTO;
import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link CartItemEntity}
 */
@Builder
public record CartItemDto(Long id, ProductCatalogDTO product, Integer quantity) implements Serializable { }