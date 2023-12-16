package com.gameshop.www.eCommerce.service;

import com.gameshop.www.eCommerce.dao.ProductDAO;
import com.gameshop.www.eCommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public Page<Product> getProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productDAO.findAll(pageRequest);
    }

    public Page<Product> getProductsByCategory(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productDAO.findByCategory_NameIgnoreCase(name, pageRequest);
    }

    public Page<Product> getRecentlyAddProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        return productDAO.findAll(pageRequest);
    }
}
