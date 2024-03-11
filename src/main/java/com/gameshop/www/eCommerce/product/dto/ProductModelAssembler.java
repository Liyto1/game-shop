package com.gameshop.www.eCommerce.product.dto;

import com.gameshop.www.eCommerce.product.controller.ProductController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler extends RepresentationModelAssemblerSupport<ProductDTO, ProductDTO> {

    public ProductModelAssembler() {
        super(ProductController.class, ProductDTO.class);
    }

    @Override
    public ProductDTO toModel(ProductDTO entity) {
        return entity.add(linkTo(methodOn(ProductController.class).getProductById(entity.getId())).withSelfRel());
    }

    @Override
    public CollectionModel<ProductDTO> toCollectionModel(Iterable<? extends ProductDTO> entities) {
        return super.toCollectionModel(entities).add(linkTo(methodOn(ProductController.class).getProducts(null, null, null, null, null, null)).withSelfRel());
    }
}
