package com.gameshop.www.eCommerce.generator.controller;

import com.gameshop.www.eCommerce.generator.service.OrderGeneratorService;
import com.gameshop.www.eCommerce.generator.service.ProductGeneratorService;
import com.gameshop.www.eCommerce.generator.service.ReviewGeneratorService;
import com.gameshop.www.eCommerce.generator.service.UserGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generate")
public class GeneratorController {

    private final ProductGeneratorService productGeneratorService;
    private final OrderGeneratorService orderGeneratorService;
    private final UserGeneratorService userGeneratorService;
    private final ReviewGeneratorService reviewGeneratorService;

    public GeneratorController(ProductGeneratorService productGeneratorService,
                               OrderGeneratorService orderGeneratorService,
                               UserGeneratorService userGeneratorService,
                               ReviewGeneratorService reviewGeneratorService) {
        this.productGeneratorService = productGeneratorService;
        this.orderGeneratorService = orderGeneratorService;
        this.userGeneratorService = userGeneratorService;
        this.reviewGeneratorService = reviewGeneratorService;
    }

    @CrossOrigin
    @PostMapping("/products")
    public ResponseEntity<Void> generateProducts() {
        productGeneratorService.generateProducts();
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PostMapping("/orders")
    public ResponseEntity<Void> generateOrders() {
        orderGeneratorService.generateOrders();
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PostMapping("/users")
    public ResponseEntity<Void> generateUsers() {
        userGeneratorService.generateUsers();
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PostMapping("/reviews")
    public ResponseEntity<Void> generateReviews() {
        reviewGeneratorService.generateReviews();
        return ResponseEntity.ok().build();
    }
}
