package com.gameshop.www.eCommerce.api.controller.product;

import com.gameshop.www.eCommerce.model.Product;
import com.gameshop.www.eCommerce.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @CrossOrigin
    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @CrossOrigin
    @GetMapping("/category/{name}")
    public ResponseEntity<Page<Product>> getProductsByCategory(@PathVariable String name,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {

        Page<Product> products = productService.getProductsByCategory(name, page, size);

        return ResponseEntity.ok(products);
    }

}
