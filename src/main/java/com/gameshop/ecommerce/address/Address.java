package com.gameshop.ecommerce.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gameshop.ecommerce.user.model.LocalUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 40)
    private String lastName;

    @Column(name = "contact_number", nullable = false, length = 10)
    private String contactNumber;

    @Column(name = "country", nullable = false, length = 60)
    private String country;

    @Column(name = "city", nullable = false, length = 60)
    private String city;

    @Column(name = "address", nullable = false, length = 120)
    private String addressLine;

    @Column(name = "postcode", nullable = false)
    private Integer postcode;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private LocalUser user;
}