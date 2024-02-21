package com.gameshop.www.eCommerce.product.dao;

import com.gameshop.www.eCommerce.product.dao.projection.SearchView;
import com.gameshop.www.eCommerce.product.dao.projection.catalog.CatalogView;
import com.gameshop.www.eCommerce.product.model.Product;
import com.gameshop.www.eCommerce.product.model.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductDAO extends JpaRepository<Product, UUID>, QuerydslPredicateExecutor<Product>,
        QuerydslBinderCustomizer<QProduct> {

    @Override
    default void customize(QuerydslBindings bindings, QProduct root) {
        bindings.bind(String.class).all((StringPath path, Collection<? extends String>
    value) -> {
            BooleanBuilder predicate = new BooleanBuilder();
            for (String s : value) {
                predicate.or(path.containsIgnoreCase(s));
            }
            return Optional.of(predicate);
        });
    }


    Page<SearchView> findByCategory_NameIgnoreCase(String name, Pageable pageable);

    Page<SearchView> findByNameContainsIgnoreCase(String name, Pageable pageable);

    Page<SearchView> findAllBy(Pageable pageable);




    @Query("select p from Product p where p.id = :id")
    Optional<Product> findByIdCustom(UUID id);

    Page<CatalogView> findAllProjectedBy(Predicate predicate, Pageable pageable);

    @Query(value = "SELECT * FROM Product ORDER BY RANDOM() LIMIT 200", nativeQuery = true)
    List<Product> findRandomProducts();

}
