package com.gameshop.www.eCommerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "products")
public class ProductCatalogDTO extends RepresentationModel<ProductCatalogDTO> {
    private UUID id;
    private String name;
    private String shortDescription;
    private Integer price;
    private String imageUrl;
    private Instant createdAt;
    private Integer priceWithSale;
    private String brand;
    private Boolean inWishlist;
    private Double rating;
    private Integer reviewCount;
}