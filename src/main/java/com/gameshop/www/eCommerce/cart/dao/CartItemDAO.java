package com.gameshop.www.eCommerce.cart.dao;

import com.gameshop.www.eCommerce.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CartItemDAO extends JpaRepository<CartItem, UUID> {
}