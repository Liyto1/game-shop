package com.gameshop.www.eCommerce.order.model;

import com.gameshop.www.eCommerce.address.Address;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "web_order")
public class WebOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<WebOrderQuantity> quantities = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private LocalUser user;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "status")
    private String status;

    //column for order number
    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "order_time")
    @TimeZoneStorage(TimeZoneStorageType.AUTO)
    private ZonedDateTime orderTime;

}