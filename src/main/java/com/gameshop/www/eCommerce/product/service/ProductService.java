package com.gameshop.www.eCommerce.product.service;

import com.gameshop.www.eCommerce.product.dao.ProductDAO;
import com.gameshop.www.eCommerce.product.dao.projection.SearchView;
import com.gameshop.www.eCommerce.product.dao.projection.catalog.CatalogView;
import com.gameshop.www.eCommerce.product.model.Product;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public Page<Product> getProducts(Predicate predicate, Pageable pageable) {
        return productDAO.findAll(predicate, pageable);
    }

    public Page<SearchView> getProductsByCategory(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productDAO.findByCategory_NameIgnoreCase(name, pageRequest);
    }

    public Page<SearchView> getRecentlyAddProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        return productDAO.findAllBy(pageRequest);
    }

    public Page<SearchView> searchProductContains(int page, int size, String name) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productDAO.findByNameContainsIgnoreCase(name, pageRequest);
    }

    public <T> Optional<Product> getProductById(UUID id) {
        return productDAO.findByIdCustom(id);
    }

    public Page<CatalogView> getMostPurchasedProducts(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("purchased")));
        return productDAO.findProductWithMaxPurchase(pageRequest);
    }
}
//todo: recommended and best seller