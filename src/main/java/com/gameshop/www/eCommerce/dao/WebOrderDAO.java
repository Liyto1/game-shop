package com.gameshop.www.eCommerce.dao;

import com.gameshop.www.eCommerce.model.LocalUser;
import com.gameshop.www.eCommerce.model.WebOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WebOrderDAO extends JpaRepository<WebOrder, UUID> {
    List<WebOrder> findByUser(LocalUser user);

}
