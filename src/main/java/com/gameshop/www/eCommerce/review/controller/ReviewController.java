package com.gameshop.www.eCommerce.review.controller;

import com.gameshop.www.eCommerce.review.model.dto.ReviewMainPageDTO;
import com.gameshop.www.eCommerce.review.model.dto.ReviewModelAssembler;
import com.gameshop.www.eCommerce.review.service.ReviewMapperService;
import com.gameshop.www.eCommerce.review.service.ReviewService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewModelAssembler reviewModelAssembler;
    private final ReviewMapperService reviewMapperService;
    private final ReviewService reviewService;

    public ReviewController(ReviewModelAssembler reviewModelAssembler, ReviewMapperService reviewMapperService, ReviewService reviewService) {
        this.reviewModelAssembler = reviewModelAssembler;
        this.reviewMapperService = reviewMapperService;
        this.reviewService = reviewService;
    }

    @CrossOrigin
    @GetMapping("/best-rate")
    public ResponseEntity<CollectionModel<ReviewMainPageDTO>> getBestRateReview() {
        List<ReviewMainPageDTO> reviewMainPageDTOS = reviewService.getTopRateReviews().stream().map(reviewMapperService::toModel).toList();

        CollectionModel<ReviewMainPageDTO> collectionModel = reviewModelAssembler.toCollectionModel(reviewMainPageDTOS);
        return ResponseEntity.ok(collectionModel);
    }
}
