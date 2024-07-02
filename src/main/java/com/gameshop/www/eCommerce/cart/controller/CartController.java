package com.gameshop.www.eCommerce.cart.controller;

import com.gameshop.www.eCommerce.cart.model.Cart;
import com.gameshop.www.eCommerce.cart.model.CartDto;
import com.gameshop.www.eCommerce.cart.service.CartService;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController("/cart")
public class CartController {

    private final CartService cartService;


    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@AuthenticationPrincipal LocalUser user, @RequestBody List<CartDto> productMapWithQuantity) {
        Cart cart = cartService.addProductToCart(user, productMapWithQuantity);
        return ResponseEntity.ok(cart);
    }


    @PostMapping("/clear")
    public ResponseEntity<Cart> clearCart(@AuthenticationPrincipal LocalUser user) {
        Cart cart = cartService.clearCart(user);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/details")
    public ResponseEntity<Cart> getCartDetails(@AuthenticationPrincipal LocalUser user) {
        Cart cart = cartService.getCartDetails(user);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/remove")
    public ResponseEntity<Cart> removeFromCart(@AuthenticationPrincipal LocalUser user, @RequestBody List<CartDto> cartDtos) {
        Cart cart = cartService.removeProductFromCart(user, cartDtos);
        return ResponseEntity.ok(cart);
    }
}
