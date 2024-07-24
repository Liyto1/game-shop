package com.gameshop.ecommerce.review.store.mapper;

import com.gameshop.ecommerce.review.store.dto.ReviewMainPageDTO;
import com.gameshop.ecommerce.review.store.ReviewEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ReviewMapper implements Function<ReviewEntity, ReviewMainPageDTO> {
    @Override
    public ReviewMainPageDTO apply(ReviewEntity reviewEntity) {
        return ReviewMainPageDTO.builder()
                .id(reviewEntity.getId())
                .productId(reviewEntity.getProduct().getId())
                .rate(reviewEntity.getRate())
                .comment(reviewEntity.getComment())
                .userName(reviewEntity.getLocalUserEntity().getFirstName())
                .userPhoto(reviewEntity.getLocalUserEntity().getUserPhoto())
                .build();
    }
}