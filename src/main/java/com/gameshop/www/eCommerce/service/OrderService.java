package com.gameshop.www.eCommerce.service;

import com.gameshop.www.eCommerce.dao.WebOrderDAO;
import com.gameshop.www.eCommerce.model.LocalUser;
import com.gameshop.www.eCommerce.model.WebOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private WebOrderDAO webOrderDAO;

    public OrderService(WebOrderDAO webOrderDAO) {
        this.webOrderDAO = webOrderDAO;
    }

    public List<WebOrder> getOrders(LocalUser user) {
        return webOrderDAO.findByUser(user);
    }
}
