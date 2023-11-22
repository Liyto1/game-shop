package com.gameshop.www.eCommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "long_description", length = 1500)
    private String longDescription;

    @Column(name = "price", nullable = false)
    private Double price;

    @OneToOne(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Inventory inventory;

}