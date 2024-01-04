package com.gameshop.www.eCommerce.dao.projection;

import com.gameshop.www.eCommerce.model.Brand;
import com.gameshop.www.eCommerce.model.Category;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public interface ProductCatalogProj {
    UUID getId();

    String getName();

    String getShortDescription();

    Double getPrice();

    String getImageUrl();

    Integer getPriceWithSale();

    Map<String, Object> getCharacteristics();

    Instant getCreatedAt();

    Brand getBrand();

    Category getCategory();

}
