package com.gameshop.www.eCommerce.order.model;

import com.gameshop.www.eCommerce.address.dto.AddressDto;
import com.gameshop.www.eCommerce.cart.model.CartBody;
import lombok.Data;

import java.util.List;

@Data
public class OrderBody {
    private AddressDto addressDto;
    private List<CartBody> cartBodyList;
    private String shippingMethod;
}
