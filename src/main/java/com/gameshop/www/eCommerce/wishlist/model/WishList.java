package com.gameshop.www.eCommerce.wishlist.model;

import com.gameshop.www.eCommerce.product.model.Product;
import com.gameshop.www.eCommerce.user.model.LocalUser;
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
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Product product;

    @NonNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "local_user_id", nullable = false)
    private LocalUser localUser;

}