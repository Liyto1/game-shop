package com.gameshop.ecommerce.cart.store;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gameshop.ecommerce.user.store.LocalUserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(orphanRemoval = true)
    private LocalUserEntity user;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItemEntity> cartItemEntities = new LinkedHashSet<>();

}