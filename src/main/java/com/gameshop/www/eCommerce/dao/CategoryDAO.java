package com.gameshop.www.eCommerce.dao;

import com.gameshop.www.eCommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryDAO extends JpaRepository<Category, UUID> {
    Category findByNameIgnoreCase(String name);
}
