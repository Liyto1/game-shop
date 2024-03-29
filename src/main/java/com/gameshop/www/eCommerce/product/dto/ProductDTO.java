package com.gameshop.www.eCommerce.product.dto;

import com.gameshop.www.eCommerce.product.model.Brand;
import com.gameshop.www.eCommerce.product.model.Category;
import com.gameshop.www.eCommerce.product.model.Inventory;
import com.gameshop.www.eCommerce.review.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "products")
public class ProductDTO extends RepresentationModel<ProductDTO> {
    private UUID id;
    private String name;
    private Integer price;
    private String imageUrl;
    private Instant createdAt;
    private Integer priceWithSale;
    private String brand;
}
/*
export interface Props {
  topLabel?: string;
  bottomLabel?: string;
  brand: name: string;
  name: string;
  shortDescription: string;
  price: number;
  priceWithSale?: number;
  imageUrl: string;
  id: string;
}
 */