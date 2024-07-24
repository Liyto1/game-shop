package com.gameshop.ecommerce.generator.controller;

import com.gameshop.ecommerce.generator.service.OrderGeneratorService;
import com.gameshop.ecommerce.generator.service.ProductGeneratorService;
import com.gameshop.ecommerce.generator.service.UserGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/generate")
public class GeneratorController {

    private final ProductGeneratorService productGeneratorService;
    private final OrderGeneratorService orderGeneratorService;
    private final UserGeneratorService userGeneratorService;

    private static final String PRODUCTS = "/products";
    private static final String ORDERS = "/orders";
    private static final String USERS = "/users";

    @PostMapping(PRODUCTS)
    public void generateProducts() {
        productGeneratorService.generateProducts();
    }

    @PostMapping(ORDERS)
    public void generateOrders() {
        orderGeneratorService.generateOrders();
    }

    @PostMapping(USERS)
    public void generateUsers() {
        userGeneratorService.generateUsers();
    }
}