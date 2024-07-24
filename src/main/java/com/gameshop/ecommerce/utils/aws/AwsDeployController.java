package com.gameshop.ecommerce.utils.aws;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AwsDeployController {
    @GetMapping("/")
    public String healthCheck() {
        return "All`s ok";
    }
}