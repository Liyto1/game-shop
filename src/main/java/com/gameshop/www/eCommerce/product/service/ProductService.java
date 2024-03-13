package com.gameshop.www.eCommerce.product.service;

import com.gameshop.www.eCommerce.order.dao.WebOrderQuantityDAO;
import com.gameshop.www.eCommerce.order.purchase.PurchaseProj;
import com.gameshop.www.eCommerce.product.dao.ProductDAO;
import com.gameshop.www.eCommerce.product.dao.projection.SearchView;
import com.gameshop.www.eCommerce.product.model.Product;
import com.gameshop.www.eCommerce.product.model.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductDAO productDAO;
    private final String PREFIX = "characteristics.";
    private final WebOrderQuantityDAO webOrderQuantityDAO;

    public ProductService(ProductDAO productDAO, WebOrderQuantityDAO webOrderQuantityDAO) {
        this.productDAO = productDAO;
        this.webOrderQuantityDAO = webOrderQuantityDAO;
    }

    public Page<Product> getProducts(Predicate predicate, Pageable pageable, Map<String, String> allRequestParams) {
        Predicate builder = createPredicateQuery(predicate, allRequestParams);
        return productDAO.findAll(builder, pageable);
    }

    public <T> Optional<Product> getProductById(UUID id) {
        return productDAO.findByIdCustom(id);
    }

    private Predicate createPredicateQuery(Predicate predicate, Map<String, String> allRequestParams) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(predicate);
        allRequestParams.entrySet().stream()
                .filter(e -> e.getKey().startsWith(PREFIX))
                .forEach(e -> {
                    var key = e.getKey().substring(PREFIX.length());
                    builder.and(QProduct.product.characteristics.contains(key, e.getValue()));
                });
        return builder;
    }
    public List<Product> getMostPurchasedProducts() {
        List<PurchaseProj> productPurchases = webOrderQuantityDAO.findTopPurchasedProducts();
        List<UUID> ids = productPurchases.stream()
                .map(PurchaseProj::getId)
                .collect(Collectors.toList());

        return productDAO.findAllByIdInOrder(ids);
    }
}
//todo: recommended