package com.gameshop.ecommerce.review.service;

import com.gameshop.ecommerce.review.model.dto.ReviewMainPageDTO;
import com.gameshop.ecommerce.review.model.Review;
import org.springframework.stereotype.Service;

@Service
public class ReviewMapperService {

    public ReviewMainPageDTO toModel(Review review) {
        ReviewMainPageDTO reviewMainPageDTO = new ReviewMainPageDTO();
        reviewMainPageDTO.setId(review.getId());
        reviewMainPageDTO.setProductId(review.getProduct().getId());
        reviewMainPageDTO.setRate(review.getRate());
        reviewMainPageDTO.setComment(review.getComment());
        reviewMainPageDTO.setUserName(review.getLocalUser().getFirstName());
        reviewMainPageDTO.setUserPhoto(review.getLocalUser().getUserPhoto());
        return reviewMainPageDTO;
    }
}
