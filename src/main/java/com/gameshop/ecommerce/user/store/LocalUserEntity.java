package com.gameshop.ecommerce.user.store;

import com.gameshop.ecommerce.address.store.AddressEntity;
import com.gameshop.ecommerce.auth.models.VerificationToken;
import com.gameshop.ecommerce.cart.store.CartEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class LocalUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AddressEntity> addressEntities = new ArrayList<>();

    @OrderBy("id desc")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VerificationToken> verificationTokens = new ArrayList<>();

    @Column(name = "email_verified", nullable = false)
    private Boolean isEmailVerified = false;

    private String userPhoto;

    @Column(nullable = false)
    private String authType;

    @Column(name = "auth_provider")
    private String authProvider;

    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private CartEntity cartEntity;
}