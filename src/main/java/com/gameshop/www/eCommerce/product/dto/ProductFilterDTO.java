package com.gameshop.www.eCommerce.product.dto;

import com.gameshop.www.eCommerce.product.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterDTO {
    Map<Brand, String> brands;
    Map<String, Integer> price;
    Map<String, List<String>> characteristics;
    Map<String, Boolean> isPresent;
    Map<String, Boolean> isSale;
}
