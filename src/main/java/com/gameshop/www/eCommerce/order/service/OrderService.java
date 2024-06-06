package com.gameshop.www.eCommerce.order.service;

import com.gameshop.www.eCommerce.order.dao.WebOrderDAO;
import com.gameshop.www.eCommerce.order.model.WebOrder;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final WebOrderDAO webOrderDAO;

    public OrderService(WebOrderDAO webOrderDAO) {
        this.webOrderDAO = webOrderDAO;
    }

    public List<WebOrder> getOrders(LocalUser user) {
        return webOrderDAO.findByUser(user);
    }
}
