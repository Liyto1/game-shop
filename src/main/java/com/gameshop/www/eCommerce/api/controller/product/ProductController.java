package com.gameshop.www.eCommerce.api.controller.product;

import com.gameshop.www.eCommerce.model.Product;
import com.gameshop.www.eCommerce.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

//    @GetMapping("/product/category/{name}")
//    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String name) {
//        return ResponseEntity.ok(productService.getProductsByCategory(name));
//    }
//
//    @GetMapping
//    public Product getProductById(UUID id) {
//        return productService.getProductById(id);
//    }
//
//    @GetMapping
//    public List<Product> getProductsWithDiscount() {
//        return productService.getProductsWithDiscount();
//    }
//

}
