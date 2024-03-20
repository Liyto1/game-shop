package com.gameshop.www.eCommerce.generator.service;

import com.gameshop.www.eCommerce.product.dao.ProductDAO;
import com.gameshop.www.eCommerce.review.dao.ReviewDAO;
import org.springframework.stereotype.Service;

@Service
public class ReviewGeneratorService {
    private final ReviewDAO reviewDAO;
    private final ProductDAO productDAO;


    public ReviewGeneratorService(ReviewDAO reviewDAO, ProductDAO productDAO) {
        this.reviewDAO = reviewDAO;
        this.productDAO = productDAO;
    }

    public void generateReviews() {

    }
}
