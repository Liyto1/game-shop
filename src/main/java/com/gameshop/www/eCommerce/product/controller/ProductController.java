package com.gameshop.www.eCommerce.product.controller;


import com.gameshop.www.eCommerce.product.dto.ProductDTO;
import com.gameshop.www.eCommerce.product.dto.ProductModelAssembler;
import com.gameshop.www.eCommerce.product.filter.FilterService;
import com.gameshop.www.eCommerce.product.filter.ProductFilterDTO;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapperService productMapperService;
    private final ProductModelAssembler productModelAssembler;
    private final PagedResourcesAssembler<ProductDTO> pagedResourcesAssembler;
    private final FilterService filterService;

    public ProductController(ProductService productService, ProductMapperService productMapperService,
                             ProductModelAssembler productModelAssembler,
                             PagedResourcesAssembler<ProductDTO> pagedResourcesAssembler, FilterService filterService) {
        this.productService = productService;
        this.productMapperService = productMapperService;
        this.productModelAssembler = productModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.filterService = filterService;
    }

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<PagedModel<ProductDTO>> getProducts(@QuerydslPredicate(root = Product.class) Predicate predicate,
                                                              @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                                              @RequestParam(name = "size", defaultValue = "15", required = false) Integer size,
                                                              @RequestParam(name = "sort", defaultValue = "UNSORTED", required = false)
                                                              String sort,
                                                              @RequestParam Map<String, String> allRequestParams,
                                                              Pageable pageable) {

        Page<ProductDTO> products = productService.getProducts(predicate, pageable, allRequestParams)
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
        //todo: check rest doc response
    }

    @CrossOrigin
    @GetMapping("/filters")
    public ResponseEntity<ProductFilterDTO> getFilters(@QuerydslPredicate(root = Product.class) Predicate predicate) {
        ProductFilterDTO productFilterDTO = filterService.getFilters(predicate);
        return ResponseEntity.ok(productFilterDTO);
    }


    @CrossOrigin
    @GetMapping("/most-purchase")
    public ResponseEntity<List<Product>> getMostPurchasedProducts() {

        List<Product> products = productService.getMostPurchasedProducts();

        return ResponseEntity.ok(products);
    }


}
