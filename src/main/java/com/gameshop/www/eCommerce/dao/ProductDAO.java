package com.gameshop.www.eCommerce.dao;

import com.gameshop.www.eCommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductDAO extends JpaRepository<Product, UUID> {
    Page<Product> findByCategory_NameIgnoreCase(String name, Pageable pageable);


}
