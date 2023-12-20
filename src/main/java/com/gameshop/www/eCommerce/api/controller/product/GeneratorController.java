package com.gameshop.www.eCommerce.api.controller.product;

import com.gameshop.www.eCommerce.service.GeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generate")
public class GeneratorController {

    private GeneratorService generatorService;

    public GeneratorController(GeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @CrossOrigin
    @PostMapping()
    public ResponseEntity<Void> generateProducts() {
        generatorService.generateProducts();
        return ResponseEntity.ok().build(); // 201 Created
    }
}
