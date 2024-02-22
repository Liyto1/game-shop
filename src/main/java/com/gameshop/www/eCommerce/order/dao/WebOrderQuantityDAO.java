package com.gameshop.www.eCommerce.order.dao;

import com.gameshop.www.eCommerce.order.model.WebOrderQuantity;
import com.gameshop.www.eCommerce.order.purchase.PurchaseProj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WebOrderQuantityDAO extends JpaRepository<WebOrderQuantity, UUID> {

    @Query(value = "SELECT p.id, SUM(woq.quantity) AS total_quantity" +
            " FROM product p" +
            " JOIN web_order_quantity woq ON woq.product_id = p.id" +
            " GROUP BY p.id" +
            " ORDER BY total_quantity DESC" +
            " LIMIT 12", nativeQuery = true)
    List<PurchaseProj> findTopPurchasedProducts();
}
