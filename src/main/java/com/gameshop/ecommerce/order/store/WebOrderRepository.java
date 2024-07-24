package com.gameshop.ecommerce.order.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WebOrderRepository extends JpaRepository<WebOrderEntity, UUID> { }