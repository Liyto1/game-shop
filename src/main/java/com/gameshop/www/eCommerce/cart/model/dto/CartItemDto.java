package com.gameshop.www.eCommerce.cart.model.dto;

import com.gameshop.www.eCommerce.product.dto.ProductCatalogDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.gameshop.www.eCommerce.cart.model.CartItem}
 */
@Data
public class CartItemDto implements Serializable {
   private UUID id;
   private ProductCatalogDTO product;
   private Integer quantity;
}