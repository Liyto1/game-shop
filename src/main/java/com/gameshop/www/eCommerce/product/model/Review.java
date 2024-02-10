package com.gameshop.www.eCommerce.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.NumericField;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Indexed
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Field
    @NumericField
    @Column(name = "rate", nullable = false)
    private Integer rate;

    @Field
    @Column(name = "comment", length = 1000)
    private String comment;

    @JsonIgnore
    @ManyToOne
    @ContainedIn
    @JoinColumn(name = "product_id")
    private Product product;

}