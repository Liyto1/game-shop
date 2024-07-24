package com.gameshop.ecommerce.order.store;

import com.gameshop.ecommerce.user.store.LocalUserEntity;
import com.gameshop.ecommerce.address.store.AddressEntity;
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
@Table(name = "web_order")
public class WebOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<WebOrderQuantityEntity> quantities = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private LocalUserEntity user;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressEntity addressEntity;

}