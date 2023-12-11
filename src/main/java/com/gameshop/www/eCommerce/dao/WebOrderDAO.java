package com.gameshop.www.eCommerce.dao;

import com.gameshop.www.eCommerce.model.LocalUser;
import com.gameshop.www.eCommerce.model.WebOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WebOrderDAO extends JpaRepository<WebOrder, UUID> {
    List<WebOrder> findByUser(LocalUser user);

}
