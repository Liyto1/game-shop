package com.gameshop.www.eCommerce.order.controller;

import com.gameshop.www.eCommerce.order.model.CheckoutInfo;
import com.gameshop.www.eCommerce.order.model.OrderBody;
import com.gameshop.www.eCommerce.order.service.OrderService;
import com.gameshop.www.eCommerce.order.service.PayService;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final PayService payService;

    @GetMapping("/checkout")
    public ResponseEntity<CheckoutInfo> checkout(@AuthenticationPrincipal LocalUser user) {
        return ResponseEntity.ok(orderService.checkout(user));
    }

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@AuthenticationPrincipal LocalUser user, OrderBody orderBody) {
        return ResponseEntity.ok(payService.initiatePayment(orderService.placeOrder(user, orderBody)));
    }

}
