package com.gameshop.www.eCommerce.generator;

import com.gameshop.www.eCommerce.order.service.OrderGeneratorService;
import com.gameshop.www.eCommerce.product.service.GeneratorService;
import com.gameshop.www.eCommerce.user.service.UserGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generate")
public class GeneratorController {

    private final GeneratorService generatorService;
    private final OrderGeneratorService orderGeneratorService;
    private final UserGeneratorService userGeneratorService;

    public GeneratorController(GeneratorService generatorService,
                               OrderGeneratorService orderGeneratorService,
                               UserGeneratorService userGeneratorService) {
        this.generatorService = generatorService;
        this.orderGeneratorService = orderGeneratorService;
        this.userGeneratorService = userGeneratorService;
    }

    @CrossOrigin
    @PostMapping("/products")
    public ResponseEntity<Void> generateProducts() {
        generatorService.generateProducts();
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
}
