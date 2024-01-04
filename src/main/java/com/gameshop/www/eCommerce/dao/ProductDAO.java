package com.gameshop.www.eCommerce.dao;

import com.gameshop.www.eCommerce.dao.projection.ProductCatalogProj;
import com.gameshop.www.eCommerce.dao.projection.ProductSearchProj;
import com.gameshop.www.eCommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductDAO extends JpaRepository<Product, UUID> {
    Page<ProductCatalogProj> findByCategory_NameIgnoreCase(String name, Pageable pageable);

    Page<ProductSearchProj> findByNameContainsIgnoreCase(String name, Pageable pageable);

    Page<ProductCatalogProj> findAllProjectedBy(Pageable pageable);

}
