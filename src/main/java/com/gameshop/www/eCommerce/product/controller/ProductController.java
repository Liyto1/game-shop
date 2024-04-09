package com.gameshop.www.eCommerce.product.controller;


import com.gameshop.www.eCommerce.product.dto.ProductCatalogDTO;
import com.gameshop.www.eCommerce.product.dto.ProductDetailDTO;
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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapperService productMapperService;
    private final ProductModelAssembler productModelAssembler;
    private final PagedResourcesAssembler<ProductCatalogDTO> pagedResourcesAssembler;
    private final FilterService filterService;

    public ProductController(ProductService productService, ProductMapperService productMapperService,
                             ProductModelAssembler productModelAssembler,
                             PagedResourcesAssembler<ProductCatalogDTO> pagedResourcesAssembler,
                             FilterService filterService) {
        this.productService = productService;
        this.productMapperService = productMapperService;
        this.productModelAssembler = productModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.filterService = filterService;
    }

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<PagedModel<ProductCatalogDTO>> getProducts(@QuerydslPredicate(root = Product.class) Predicate predicate,
                                                                     @RequestParam(required = false) Map<String, String> allRequestParams,
                                                                     Pageable pageable) {

        Page<ProductCatalogDTO> products = productService.getProducts(predicate, pageable, allRequestParams)
                .map(productMapperService::toModel);
        PagedModel<ProductCatalogDTO> pagedModel = pagedResourcesAssembler.toModel(products, productModelAssembler);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductDetailDTO>> getProductById(@PathVariable UUID id) {

        return productService.getProductById(id)
                .map(productMapperService::toModelDetail)
                .map(productModelAssembler::toDetailModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @GetMapping("/filters")
    public ResponseEntity<ProductFilterDTO> getFilters(@QuerydslPredicate(root = Product.class) Predicate predicate) {
        ProductFilterDTO productFilterDTO = filterService.getFilters(predicate);
        return ResponseEntity.ok(productFilterDTO);
    }


    @CrossOrigin
    @GetMapping("/most-purchase")
    public ResponseEntity<CollectionModel<ProductCatalogDTO>> getMostPurchasedProducts() {
        List<ProductCatalogDTO> products = productService.getMostPurchasedProducts()
                .stream()
                .map(productMapperService::toModel)
                .toList();

        CollectionModel<ProductCatalogDTO> collectionModel = productModelAssembler.toCollectionModel(products);
        return ResponseEntity.ok(collectionModel);
    }


}
