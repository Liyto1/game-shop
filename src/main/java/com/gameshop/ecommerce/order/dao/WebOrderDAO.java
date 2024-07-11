package com.gameshop.ecommerce.order.dao;

import com.gameshop.ecommerce.order.model.WebOrder;
import com.gameshop.ecommerce.user.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WebOrderDAO extends JpaRepository<WebOrder, UUID> {
    List<WebOrder> findByUser(LocalUser user);


}
