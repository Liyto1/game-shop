package com.gameshop.www.eCommerce.api.controller.product;


import com.gameshop.www.eCommerce.dao.projection.SearchView;
import com.gameshop.www.eCommerce.dao.projection.catalog.CatalogView;
import com.gameshop.www.eCommerce.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<Page<SearchView>> getProducts(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "12") int size) {

        Page<SearchView> products = productService.getProducts(page, size);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(products);
        }
        return ResponseEntity.ok(products);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<CatalogView> getProductById(@PathVariable UUID id) {
        Optional<CatalogView> product = productService.getProductById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(product.get());
    }

    @CrossOrigin
    @GetMapping("/by-category/{categoryName}")
    public ResponseEntity<Page<SearchView>> getProductsByCategory(@PathVariable String categoryName,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {

        Page<SearchView> products = productService.getProductsByCategory(categoryName, page, size);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(products);
        }
        return ResponseEntity.ok(products);
    }

    @CrossOrigin
    @GetMapping("/recently")
    public ResponseEntity<Page<SearchView>> getRecentlyAddProducts(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "4") int size) {
        Page<SearchView> products = productService.getRecentlyAddProducts(page, size);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(products);
        }
        return ResponseEntity.ok(products);
    }

    @CrossOrigin
    @GetMapping("/search")
    public ResponseEntity<Page<SearchView>> searchProductContains(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "4") int size,
                                                                  @RequestParam("name") String name) {

        Page<SearchView> products = productService.searchProductContains(page, size, name);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(products);
        }
        return ResponseEntity.ok(products);
    }


}
