package com.gameshop.www.eCommerce.product.controller;


import com.gameshop.www.eCommerce.product.dao.projection.SearchView;
import com.gameshop.www.eCommerce.product.dto.ProductDTO;
import com.gameshop.www.eCommerce.product.dto.ProductModelAssembler;
import com.gameshop.www.eCommerce.product.model.Product;
import com.gameshop.www.eCommerce.product.service.ProductMapperService;
import com.gameshop.www.eCommerce.product.service.ProductService;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapperService productMapperService;
    private final ProductModelAssembler productModelAssembler;
    private final PagedResourcesAssembler<ProductDTO> pagedResourcesAssembler;

    public ProductController(ProductService productService, ProductMapperService productMapperService,
                             ProductModelAssembler productModelAssembler,
                             PagedResourcesAssembler<ProductDTO> pagedResourcesAssembler) {
        this.productService = productService;
        this.productMapperService = productMapperService;
        this.productModelAssembler = productModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<PagedModel<ProductDTO>> getProducts(@QuerydslPredicate(root = Product.class) Predicate predicate,
                                                              @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                                              @RequestParam(name = "size", defaultValue = "12", required = false) Integer size,
                                                              @RequestParam(name = "sort", defaultValue = "UNSORTED", required = false)
                                                              String sort, Pageable pageable) {
        Page<ProductDTO> products = productService.getProducts(predicate, pageable)
                .map(productMapperService::toModel);
        PagedModel<ProductDTO> pagedModel = pagedResourcesAssembler.toModel(products, productModelAssembler);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable UUID id) {
        ProductDTO product = productMapperService.toModel(productService.getProductById(id).orElseThrow(() -> new IllegalArgumentException("Incorrect id " + id)));
        product.add(linkTo(ProductController.class).slash(product.getId()).withSelfRel());
        return product;
    }

    @CrossOrigin
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<Page<SearchView>> getProductsByCategory(@PathVariable String categoryName,
                                                                  @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                                                  @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {

        Page<SearchView> products = productService.getProductsByCategory(categoryName, page, size);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(products);
        }
        return ResponseEntity.ok(products);
    }

    @CrossOrigin
    @GetMapping("/recently")
    public ResponseEntity<Page<SearchView>> getRecentlyAddProducts(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                                                   @RequestParam(name = "size", defaultValue = "4", required = false) Integer size) {
        Page<SearchView> products = productService.getRecentlyAddProducts(page, size);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(products);
        }
        return ResponseEntity.ok(products);
    }

    @CrossOrigin
    @GetMapping("/search")
    public ResponseEntity<Page<SearchView>> searchProductContains(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                                                  @RequestParam(name = "size", defaultValue = "4", required = false) Integer size,
                                                                  @RequestParam("name") String name) {

        Page<SearchView> products = productService.searchProductContains(page, size, name);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(products);
        }
        return ResponseEntity.ok(products);
    }


}
