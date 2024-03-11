package com.gameshop.www.eCommerce.product.filter;

import com.gameshop.www.eCommerce.product.dao.BrandDAO;
import com.gameshop.www.eCommerce.product.dao.ProductDAO;
import com.gameshop.www.eCommerce.product.model.Brand;
import com.gameshop.www.eCommerce.product.model.Product;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FilterService {

    private final ProductDAO productDAO;
    private final BrandDAO brandDAO;

    public FilterService(ProductDAO productDAO, BrandDAO brandDAO) {
        this.productDAO = productDAO;
        this.brandDAO = brandDAO;
    }

    public ProductFilterDTO getFilters(Predicate predicate) {
        List<Product> filteredProducts = StreamSupport.stream(productDAO.findAll(predicate).spliterator(), false)
                .toList();

        Set<String> brands = brandDAO.findAll().stream()
                .map(Brand::getName).collect(Collectors.toSet());

        Map<String, Set<String>> characteristics = filteredProducts.stream()
                .flatMap(product -> product.getCharacteristics().entrySet().stream())
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(entry -> String.valueOf(entry.getValue()), Collectors.toSet())
                ));

        Set<String> selectedCharacteristics = filteredProducts.stream()
                .flatMap(p -> p.getCharacteristics().keySet().stream())
                .collect(Collectors.toSet());
        List<Product> productsWithSelectedCharacteristics = filteredProducts.stream()
                .filter(p -> p.getCharacteristics().keySet().stream()
                        .anyMatch(selectedCharacteristics::contains))
                .toList();
        int minPrice = productsWithSelectedCharacteristics.stream()
                .mapToInt(Product::getPrice)
                .min().orElse(0);

        int maxPrice = productsWithSelectedCharacteristics.stream()
                .mapToInt(Product::getPrice)
                .max().orElse(0);

        Set<Boolean> isPresentSet = productsWithSelectedCharacteristics.stream()
                .map(product -> product.getInventory() != null && product.getInventory().getQuantity() > 0)
                .collect(Collectors.toSet());

        Set<Boolean> isSaleSet = productsWithSelectedCharacteristics.stream()
                .map(product -> product.getPriceWithSale() != null)
                .collect(Collectors.toSet());

        Map<String, Integer> prices = new HashMap<>();
        prices.put("min_price", minPrice);
        prices.put("max_price", maxPrice);
        return new ProductFilterDTO(brands, prices, characteristics, isPresentSet, isSaleSet);
    }


}
