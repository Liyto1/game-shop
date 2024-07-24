package com.gameshop.ecommerce.generator.service;

import com.gameshop.ecommerce.order.store.WebOrderEntity;
import com.gameshop.ecommerce.order.store.WebOrderQuantityEntity;
import com.gameshop.ecommerce.user.store.LocalUserRepository;
import com.gameshop.ecommerce.user.store.LocalUserEntity;
import com.gameshop.ecommerce.order.store.WebOrderRepository;
import com.gameshop.ecommerce.order.store.WebOrderQuantityRepository;
import com.gameshop.ecommerce.product.dao.ProductDAO;
import com.gameshop.ecommerce.product.model.Product;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderGeneratorService {

    private final LocalUserRepository localUserRepository;
    private final WebOrderRepository webOrderRepository;
    private final ProductDAO productDAO;
    private final WebOrderQuantityRepository webOrderQuantityRepository;

    private final List<LocalUserEntity> users = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();
    private final List<WebOrderQuantityEntity> webOrderQuantities = new ArrayList<>();

    public void generateOrders() {
        final var faker = new Faker();
        final var orders = new ArrayList<WebOrderEntity>();

        if (users.isEmpty()) {
            fillData();
        }

        for (int i = 0; i < 50; i++) {
            WebOrderEntity order = createOrder(faker);
            orders.add(order);
            webOrderQuantities.addAll(order.getQuantities());
        }

        webOrderRepository.saveAll(orders);
        webOrderQuantityRepository.saveAll(webOrderQuantities);
    }

    private WebOrderEntity createOrder(Faker faker) {
        final var quantities = new ArrayList<WebOrderQuantityEntity>();

        final var order = WebOrderEntity.builder()
                .quantities(quantities)
                .user(users.get(faker.number().numberBetween(0, users.size() - 1)))
                .build();

        for (int i = 0; i < faker.number().numberBetween(1, 5); i++) {
            quantities.add(createOrderQuantity(faker, order));
        }

        return order;
    }

    private WebOrderQuantityEntity createOrderQuantity(Faker faker, WebOrderEntity webOrderEntity) {
        return WebOrderQuantityEntity.builder()
                .quantity(faker.number().numberBetween(1, 6))
                .product(products.get(faker.number().numberBetween(0, products.size() - 1)))
                .order(webOrderEntity)
                .build();
    }

    private void fillData() {
        localUserRepository.findAll();
        productDAO.findRandomProducts();
    }
}