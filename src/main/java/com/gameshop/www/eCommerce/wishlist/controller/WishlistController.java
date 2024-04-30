package com.gameshop.www.eCommerce.wishlist.controller;

import com.gameshop.www.eCommerce.user.model.LocalUser;
import com.gameshop.www.eCommerce.wishlist.model.WishList;
import com.gameshop.www.eCommerce.wishlist.service.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<Object> addProductToWishlist(@PathVariable UUID productId,
                                                       @AuthenticationPrincipal LocalUser user) {
        if (user != null) {
            wishlistService.addProductToWishlist(productId, user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product added to wishlist " + user.getEmail());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Object> removeProductFromWishlist(@PathVariable UUID productId,
                                                            @AuthenticationPrincipal LocalUser user) {
        if (user != null) {
            wishlistService.removeProductFromWishlist(productId, user);
            return ResponseEntity.status(HttpStatus.OK).body("Product removed from wishlist " + user.getEmail());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getWishlist(@AuthenticationPrincipal LocalUser user) {
        if (user != null) {
            List<WishList> wishList = wishlistService.getUserWishlist(user);
            return ResponseEntity.status(HttpStatus.OK).body(wishList);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
