package com.gameshop.www.eCommerce.order.dao;

import com.gameshop.www.eCommerce.order.model.WebOrder;
import com.gameshop.www.eCommerce.order.model.WebOrderQuantity;
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


}
