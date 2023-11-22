package com.gameshop.www.eCommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "adress")
public class Adress {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "city", nullable = false, length = 60)
    private String city;

    @Column(name = "region", nullable = false, length = 60)
    private String region;

    @Column(name = "post_office", nullable = false, length = 150)
    private String postOffice;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private LocalUser user;

}