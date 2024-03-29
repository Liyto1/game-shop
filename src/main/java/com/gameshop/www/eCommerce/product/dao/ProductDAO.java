package com.gameshop.www.eCommerce.product.dao;

import com.gameshop.www.eCommerce.product.model.Product;
import com.gameshop.www.eCommerce.product.model.QProduct;
import com.gameshop.www.eCommerce.review.model.Review;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

        bindings.bind(root.price).all((path, value) -> {
                    List<? extends Integer> prices = new ArrayList<>(value);
                    if (prices.size() == 2) {
                        return Optional.of(path.between(prices.get(0), prices.get(1)));
                    }
                    return Optional.empty();
                }
        );
    }

    @Query("SELECT p FROM Product p WHERE p.id IN :ids")
    List<Product> findAllByIdInOrder(@Param("ids") List<UUID> ids);

    @Query("select p from Product p where p.id = :id")
    Optional<Product> findByIdCustom(UUID id);

    @Query(value = "SELECT * FROM Product ORDER BY RANDOM() LIMIT 200", nativeQuery = true)
    List<Product> findRandomProducts();
}
