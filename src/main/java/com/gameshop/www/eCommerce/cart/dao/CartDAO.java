package com.gameshop.www.eCommerce.cart.dao;

import com.gameshop.www.eCommerce.cart.model.Cart;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface CartDAO extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByUser(LocalUser user);

}
