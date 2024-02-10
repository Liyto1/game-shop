package com.gameshop.www.eCommerce.product.service;

import com.gameshop.www.eCommerce.product.dao.ProductDAO;
import com.gameshop.www.eCommerce.product.dao.projection.SearchView;
import com.gameshop.www.eCommerce.product.dao.projection.catalog.CatalogView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Page<SearchView> getProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productDAO.findAllBy(pageRequest);
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

    public Optional<CatalogView> getProductById(UUID id) {
        return productDAO.findProductById(id);
    }
}
//todo: fixing page bug
