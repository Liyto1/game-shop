package com.gameshop.www.eCommerce.dao;

import com.gameshop.www.eCommerce.dao.projection.SearchView;
import com.gameshop.www.eCommerce.dao.projection.catalog.CatalogView;
import com.gameshop.www.eCommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductDAO extends JpaRepository<Product, UUID> {
    Page<SearchView> findByCategory_NameIgnoreCase(String name, Pageable pageable);

    Page<SearchView> findByNameContainsIgnoreCase(String name, Pageable pageable);

    Page<SearchView> findAllBy(Pageable pageable);

    Optional<CatalogView> findProductById(UUID id);

}
