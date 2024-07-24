package com.gameshop.ecommerce.review.service;

import com.gameshop.ecommerce.review.store.ReviewEntity;
import com.gameshop.ecommerce.review.store.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<ReviewEntity> getTopRateReviews() {
        return reviewRepository.findAllByOrderByRateDesc(PageRequest.of(0, 3, Sort.by("rate").descending()));
    }
}