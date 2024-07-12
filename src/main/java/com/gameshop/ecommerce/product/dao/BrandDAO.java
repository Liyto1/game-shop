package com.gameshop.ecommerce.product.dao;

import com.gameshop.ecommerce.product.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BrandDAO extends JpaRepository<Brand, UUID> {
}