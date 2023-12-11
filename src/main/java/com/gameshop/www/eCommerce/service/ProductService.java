package com.gameshop.www.eCommerce.service;

import com.gameshop.www.eCommerce.dao.ProductDAO;
import com.gameshop.www.eCommerce.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
private ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getProducts(){
        return productDAO.findAll();
    }
}
