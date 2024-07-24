package com.gameshop.ecommerce.product.service;

import com.gameshop.ecommerce.product.dto.ProductCatalogDTO;
import com.gameshop.ecommerce.product.dto.ProductDetailDTO;
import com.gameshop.ecommerce.user.store.LocalUserEntity;
import com.gameshop.ecommerce.product.model.Product;
import com.gameshop.ecommerce.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductMapperService {
    private final WishlistService wishlistService;

    public ProductCatalogDTO toModel(Product product, LocalUserEntity user) {
        ProductCatalogDTO productCatalogDTO = new ProductCatalogDTO();
        productCatalogDTO.setId(product.getId());
        productCatalogDTO.setName(product.getName());
        productCatalogDTO.setShortDescription(product.getShortDescription());
        productCatalogDTO.setPrice(product.getPrice());
        productCatalogDTO.setImageUrl(product.getImageUrl());
        productCatalogDTO.setCreatedAt(product.getCreatedAt());
        productCatalogDTO.setPriceWithSale(product.getPriceWithSale());
        productCatalogDTO.setBrand(product.getBrand().getName());
        if (user != null) {
            productCatalogDTO.setInWishlist(wishlistService.isProductInWishlist(product.getId(), user));
        } else {
            productCatalogDTO.setInWishlist(false);
        }
        productCatalogDTO.setRating(product.getAverageRate());
        productCatalogDTO.setReviewCount(product.getReviewEntities().size());
        return productCatalogDTO;
    }

    public ProductDetailDTO toModelDetail(Product product, LocalUserEntity user) {
        ProductDetailDTO productDetailDTO = new ProductDetailDTO();
        productDetailDTO.setId(product.getId());
        productDetailDTO.setName(product.getName());
        productDetailDTO.setPrice(product.getPrice());
        productDetailDTO.setImageUrl(product.getImageUrl());
        productDetailDTO.setCreatedAt(product.getCreatedAt());
        productDetailDTO.setPriceWithSale(product.getPriceWithSale());
        productDetailDTO.setBrand(product.getBrand().getName());
        productDetailDTO.setCharacteristics(product.getCharacteristics());
        productDetailDTO.setShortDescription(product.getShortDescription());
        productDetailDTO.setLongDescription(product.getLongDescription());
        productDetailDTO.setReviewEntities(product.getReviewEntities());
        productDetailDTO.setAverageRate(product.getAverageRate());
        productDetailDTO.setCategory(product.getCategory().getName());
        if (user != null) {
            productDetailDTO.setInWishlist(wishlistService.isProductInWishlist(product.getId(), user));
        } else {
            productDetailDTO.setInWishlist(false);
        }
        return productDetailDTO;
    }
}
