package com.gameshop.ecommerce.review.controller;

import com.gameshop.ecommerce.review.store.dto.ReviewMainPageDTO;
import com.gameshop.ecommerce.review.store.dto.ReviewModelAssembler;
import com.gameshop.ecommerce.review.store.mapper.ReviewMapper;
import com.gameshop.ecommerce.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewModelAssembler reviewModelAssembler;
    private final ReviewMapper reviewMapper;
    private final ReviewService reviewService;

    @GetMapping("/best-rate")
    public CollectionModel<ReviewMainPageDTO> getBestRateReview() {
        List<ReviewMainPageDTO> reviewMainPageDTOS = reviewService.getTopRateReviews().stream()
                .map(reviewMapper).toList();

        return reviewModelAssembler.toCollectionModel(reviewMainPageDTOS);
    }
}