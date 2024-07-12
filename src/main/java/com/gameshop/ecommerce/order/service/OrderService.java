package com.gameshop.ecommerce.order.service;

import com.gameshop.ecommerce.user.model.LocalUser;
import com.gameshop.ecommerce.order.dao.WebOrderDAO;
import com.gameshop.ecommerce.order.model.WebOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final WebOrderDAO webOrderDAO;

    public List<WebOrder> getOrders(LocalUser user) {
        return webOrderDAO.findByUser(user);
    }
}
