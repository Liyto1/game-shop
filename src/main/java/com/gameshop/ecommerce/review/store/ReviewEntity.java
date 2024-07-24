package com.gameshop.ecommerce.review.store;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gameshop.ecommerce.user.store.LocalUserEntity;
import com.gameshop.ecommerce.product.model.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer rate;

    @Column(nullable = false)
    private String comment;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "local_user_id", nullable = false)
    private LocalUserEntity localUserEntity;

}