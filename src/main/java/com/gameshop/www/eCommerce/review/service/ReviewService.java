package com.gameshop.www.eCommerce.review.service;

import com.gameshop.www.eCommerce.review.dao.ReviewDAO;
import com.gameshop.www.eCommerce.review.model.Review;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewDAO reviewDAO;

    public ReviewService(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    public List<Review> getTopRateReviews() {
        Pageable topThree = PageRequest.of(0, 3, Sort.by("rate").descending());
        return reviewDAO.findAllByOrderByRateDesc(topThree);
    }
}
