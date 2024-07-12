package com.gameshop.ecommerce.wishlist.model;

import com.gameshop.ecommerce.product.model.Product;
import com.gameshop.ecommerce.user.model.LocalUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "wish_list")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NonNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "local_user_id", nullable = false)
    private LocalUser localUser;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}