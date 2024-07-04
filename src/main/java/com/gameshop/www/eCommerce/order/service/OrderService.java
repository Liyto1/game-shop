package com.gameshop.www.eCommerce.order.service;

import com.gameshop.www.eCommerce.address.service.AddressService;
import com.gameshop.www.eCommerce.cart.model.Cart;
import com.gameshop.www.eCommerce.cart.service.CartService;
import com.gameshop.www.eCommerce.order.dao.WebOrderDAO;
import com.gameshop.www.eCommerce.order.model.CheckoutInfo;
import com.gameshop.www.eCommerce.order.model.OrderBody;
import com.gameshop.www.eCommerce.order.model.WebOrder;
import com.gameshop.www.eCommerce.order.model.WebOrderQuantity;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderService {
    private final WebOrderDAO webOrderDAO;
    private final CartService cartService;
    private final AddressService addressService;


    public List<WebOrder> getOrders(LocalUser user) {
        return webOrderDAO.findByUser(user);
    }

    @Transactional
    public WebOrder placeOrder(LocalUser user, OrderBody orderBody) {
        Cart cart = cartService.getCartByUser(user);
        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        WebOrder order = new WebOrder();
        order.setUser(user);
        order.setQuantities(cart.getCartItems().stream().map(cartItem -> {
            WebOrderQuantity quantity = new WebOrderQuantity();
            quantity.setProduct(cartItem.getProduct());
            quantity.setQuantity(cartItem.getQuantity());
            quantity.setOrder(order);
            return quantity;
        }).collect(Collectors.toList()));

        double totalPrice = order.getQuantities().stream()
                .mapToDouble(q -> q.getProduct().getPrice() * q.getQuantity())
                .sum();
        order.setTotalPrice(totalPrice);
        order.setOrderNumber(generateOrderNumber());

        cartService.clearCart(user);

        return webOrderDAO.save(order);
    }

    public List<WebOrder> getOrdersForUser(LocalUser user) {
        return webOrderDAO.findByUser(user);
    }

    public CheckoutInfo checkout(LocalUser user) {
        CheckoutInfo checkoutInfo = new CheckoutInfo();
        checkoutInfo.setAddresses(addressService.getAllAddresses(user.getId()));
        checkoutInfo.setCart(cartService.getCartByUser(user));
        checkoutInfo.setUser(user);
        return checkoutInfo;
    }

    private String generateOrderNumber() {
        String number;
        do {
            number = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
        } while (webOrderDAO.findByOrderNumber(number).isPresent());
        return number;
    }

    //todo: generate html page with order for sending by email
}
