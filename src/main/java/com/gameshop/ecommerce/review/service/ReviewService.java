package com.gameshop.ecommerce.review.service;

import com.gameshop.ecommerce.review.model.Review;
import com.gameshop.ecommerce.review.dao.ReviewDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewDAO reviewDAO;

    public List<Review> getTopRateReviews() {
        Pageable topThree = PageRequest.of(0, 3, Sort.by("rate").descending());
        return reviewDAO.findAllByOrderByRateDesc(topThree);
    }
}
