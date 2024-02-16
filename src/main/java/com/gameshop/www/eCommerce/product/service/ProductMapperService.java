package com.gameshop.www.eCommerce.product.service;

import com.gameshop.www.eCommerce.product.dto.ProductDTO;
import com.gameshop.www.eCommerce.product.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapperService {
    public ProductDTO toModel(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setShortDescription(product.getShortDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setImageUrl(product.getImageUrl());
        productDTO.setCreatedAt(product.getCreatedAt());
        productDTO.setCharacteristics(product.getCharacteristics());
        productDTO.setPriceWithSale(product.getPriceWithSale());
        productDTO.setInventory(product.getInventory());
        productDTO.setBrand(product.getBrand());
        productDTO.setCategory(product.getCategory());
        productDTO.setReviews(product.getReviews());
        return productDTO;
    }
}
