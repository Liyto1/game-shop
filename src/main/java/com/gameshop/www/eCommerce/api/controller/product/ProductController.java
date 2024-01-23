package com.gameshop.www.eCommerce.api.controller.product;

import com.gameshop.www.eCommerce.dao.projection.ProductCatalogProj;
import com.gameshop.www.eCommerce.dao.projection.ProductSearchProj;
import com.gameshop.www.eCommerce.model.Product;
import com.gameshop.www.eCommerce.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<Page<ProductCatalogProj>> getProducts(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "12") int size) {

        Page<ProductCatalogProj> products = productService.getProducts(page, size);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(products);
        }
        return ResponseEntity.ok(products);
    }

    @CrossOrigin
    @GetMapping("/{categoryName}")
    public ResponseEntity<Page<ProductCatalogProj>> getProductsByCategory(@PathVariable String categoryName,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size) {

        Page<ProductCatalogProj> products = productService.getProductsByCategory(categoryName, page, size);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(products);
        }
        return ResponseEntity.ok(products);
    }

    @CrossOrigin
    @GetMapping("/recently")
    public ResponseEntity<Page<Product>> getRecentlyAddProducts(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "4") int size) {
        Page<Product> products = productService.getRecentlyAddProducts(page, size);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(products);
        }
        return ResponseEntity.ok(products);
    }

    @CrossOrigin
    @GetMapping("/search")
    public ResponseEntity<Page<ProductSearchProj>> searchProductContains(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "4") int size,
                                                                         @RequestParam("name") String name) {

        Page<ProductSearchProj> products = productService.searchProductContains(page, size, name);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(products);
        }
        return ResponseEntity.ok(products);
    }


}
