package com.gameshop.www.eCommerce.cart.model.dto;

import com.gameshop.www.eCommerce.product.dto.ProductCatalogDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.gameshop.www.eCommerce.cart.model.CartItem}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto implements Serializable {
   private UUID id;
   private ProductCatalogDTO product;
   private Integer quantity;
}