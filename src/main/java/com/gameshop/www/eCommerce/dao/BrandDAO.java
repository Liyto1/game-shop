package com.gameshop.www.eCommerce.dao;

import com.gameshop.www.eCommerce.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BrandDAO extends JpaRepository<Brand, UUID> {
    Brand findByNameIgnoreCase(String name);
}