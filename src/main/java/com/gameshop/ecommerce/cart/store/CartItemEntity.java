package com.gameshop.ecommerce.cart.store;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gameshop.ecommerce.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "carts")
@AllArgsConstructor
@NoArgsConstructor
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;

}
