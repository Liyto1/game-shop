package com.gameshop.www.eCommerce.order.service;

import com.gameshop.www.eCommerce.order.dao.WebOrderDAO;
import com.gameshop.www.eCommerce.order.model.WebOrder;
import com.gameshop.www.eCommerce.user.dao.LocalUserDAO;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderGeneratorService {

    private LocalUserDAO localUserDAO;
    private WebOrderDAO webOrderDAO;
    List<WebOrder> orders = new ArrayList<>();
    List<LocalUser> users = new ArrayList<>();
    List<String> products = new ArrayList<>();

    public OrderGeneratorService(LocalUserDAO localUserDAO, WebOrderDAO webOrderDAO) {
        this.localUserDAO = localUserDAO;
        this.webOrderDAO = webOrderDAO;
    }

    public void generateOrders() {
        Faker faker = new Faker();
        for (int i = 0; i < 50; i++) {
            WebOrder order = createOrder(faker);
        }
    }

    private WebOrder createOrder(Faker faker) {
        WebOrder order = new WebOrder();

    }
}
