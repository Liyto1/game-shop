package com.gameshop.ecommerce.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gameshop.ecommerce.address.Address;
import com.gameshop.ecommerce.auth.model.VerificationToken;
import com.gameshop.ecommerce.cart.model.Cart;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "local_user")
public class LocalUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @JsonIgnore
    @Column(name = "password", length = 1000)
    private String password;

    @Column(name = "email", nullable = false, unique = true, length = 320)
    private String email;

    @Column(name = "phone_number", length = 25)
    private String phoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @JsonIgnore
    @OrderBy("id desc")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VerificationToken> verificationTokens = new ArrayList<>();

    @JsonIgnore
    @Column(name = "email_verified", nullable = false)
    private Boolean isEmailVerified = false;

    @Column(name = "user_photo")
    private String userPhoto;

    @JsonIgnore
    @Column(name = "auth_type", nullable = false, length = 40)
    private String authType;

    @JsonIgnore
    @Column(name = "auth_provider", length = 55)
    private String authProvider;

    @JsonIgnore
    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private Cart cart;

}