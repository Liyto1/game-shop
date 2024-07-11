package com.gameshop.ecommerce.healthCheck.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HeathCheckController {
    @GetMapping
    public ResponseEntity<Object> healthCheck() {
        return ResponseEntity.ok("All`s ok");
    }
}
