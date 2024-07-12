package com.gameshop.ecommerce.cart.dao;

import com.gameshop.ecommerce.cart.model.Cart;
import com.gameshop.ecommerce.user.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartDAO extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByUser(LocalUser user);

    Optional<Cart> findByUser_Id(UUID id);
}
