package com.gameshop.www.eCommerce.order.model;

import com.gameshop.www.eCommerce.address.dto.AddressDto;
import com.gameshop.www.eCommerce.cart.model.Cart;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import lombok.Data;

import java.util.List;

@Data
public class CheckoutInfo {
    private LocalUser user;
    private List<AddressDto> addresses;
    private Cart cart;
}
