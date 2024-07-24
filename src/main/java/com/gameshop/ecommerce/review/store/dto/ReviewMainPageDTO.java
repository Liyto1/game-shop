package com.gameshop.ecommerce.review.store.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Relation(collectionRelation = "review")
public class ReviewMainPageDTO extends RepresentationModel<ReviewMainPageDTO> {
    private UUID id;
    private UUID productId;
    private Integer rate;
    private String comment;
    private String userName;
    private String userPhoto;
}
