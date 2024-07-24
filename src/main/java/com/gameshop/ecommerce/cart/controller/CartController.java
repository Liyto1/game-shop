package com.gameshop.ecommerce.cart.controller;

import com.gameshop.ecommerce.cart.store.CartEntity;
import com.gameshop.ecommerce.cart.store.dto.CartBody;
import com.gameshop.ecommerce.cart.store.dto.CartDto;
import com.gameshop.ecommerce.cart.service.CartService;
import com.gameshop.ecommerce.user.store.LocalUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @PostMapping()
    public CartDto addToCart(@AuthenticationPrincipal LocalUserEntity user,
                             @RequestBody List<CartBody> cartBodyList) {
        return cartService.addProductToCart(user, cartBodyList);
    }

    @DeleteMapping("/clear")
    public CartEntity clearCart(@AuthenticationPrincipal LocalUserEntity user) {
        return cartService.clearCart(user);
    }

    @GetMapping()
    public CartDto getCartDetails(@AuthenticationPrincipal LocalUserEntity user) {
        return cartService.getCartDetails(user);
    }

    @DeleteMapping()
    public CartEntity removeFromCart(@AuthenticationPrincipal LocalUserEntity user,
                                     @RequestBody List<CartBody> cartBodies) {
        return cartService.removeProductFromCart(user, cartBodies);
    }
}