package com.gameshop.www.eCommerce.order.dao;

import com.gameshop.www.eCommerce.order.model.WebOrder;
import com.gameshop.www.eCommerce.product.model.Product;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WebOrderDAO extends JpaRepository<WebOrder, UUID> {
    List<WebOrder> findByUser(LocalUser user);

    @Query("SELECT q.product.id, SUM(q.quantity) AS totalQuantity " +
            "FROM WebOrderQuantity q " +
            "GROUP BY q.product.id " +
            "ORDER BY totalQuantity DESC ")
    List<Product> findProductWithMaxQuantity();

}
