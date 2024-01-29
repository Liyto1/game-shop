package com.gameshop.www.eCommerce.dao.projection;

import java.util.UUID;

public interface SearchView {
    UUID getId();

    String getName();

    Double getPrice();

    String getImageUrl();

    Integer getPriceWithSale();
}
