package com.gameshop.www.eCommerce.order.dao;

import com.gameshop.www.eCommerce.order.model.WebOrderQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WebOrderQuantityDAO extends JpaRepository<WebOrderQuantity, UUID> {
}
