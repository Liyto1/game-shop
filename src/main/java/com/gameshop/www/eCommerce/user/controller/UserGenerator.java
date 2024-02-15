package com.gameshop.www.eCommerce.user.controller;

import com.gameshop.www.eCommerce.user.service.UserGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generate-users")
public class UserGenerator {

    private UserGeneratorService userGeneratorService;

    public UserGenerator(UserGeneratorService userGeneratorService) {
        this.userGeneratorService = userGeneratorService;
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<Void> generateUsers() {
        userGeneratorService.generateUsers();
        return ResponseEntity.ok().build();
    }
}
