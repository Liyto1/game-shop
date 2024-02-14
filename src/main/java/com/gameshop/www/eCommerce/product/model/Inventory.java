package com.gameshop.www.eCommerce.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @JsonIgnore
    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}