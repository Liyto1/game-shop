package com.gameshop.www.eCommerce.wishlist.controller;

import com.gameshop.www.eCommerce.user.model.LocalUser;
import com.gameshop.www.eCommerce.wishlist.model.WishList;
import com.gameshop.www.eCommerce.wishlist.service.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PostMapping("/add")
    public ResponseEntity<Object> addProductToWishlist(@RequestParam("product") UUID productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof LocalUser user) {
            user = (LocalUser) authentication.getPrincipal();
            UUID userId = user.getId();
            wishlistService.addProductToWishlist(productId, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product added to wishlist " + user.getEmail());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Object> removeProductFromWishlist(@RequestParam("product") UUID productId,
                                                            @RequestParam("token") String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof LocalUser user) {
            user = (LocalUser) authentication.getPrincipal();
            UUID userId = user.getId();
            wishlistService.removeProductFromWishlist(productId, userId);
            return ResponseEntity.status(HttpStatus.OK).body("Product removed from wishlist " + user.getEmail());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getWishlist() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof LocalUser user) {
            user = (LocalUser) authentication.getPrincipal();
            UUID userId = user.getId();
            List<WishList> wishList = wishlistService.getWishlist(userId);
            return ResponseEntity.status(HttpStatus.OK).body(wishList);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
    }
}