package com.gameshop.www.eCommerce.review.service;

import com.gameshop.www.eCommerce.review.model.Review;
import com.gameshop.www.eCommerce.review.model.dto.ReviewMainPageDTO;
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
