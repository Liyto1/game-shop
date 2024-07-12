package com.gameshop.ecommerce.wishlist.service;

import com.gameshop.ecommerce.exception.ProductNotFoundException;
import com.gameshop.ecommerce.product.model.Product;
import com.gameshop.ecommerce.user.model.LocalUser;
import com.gameshop.ecommerce.product.dao.ProductDAO;
import com.gameshop.ecommerce.wishlist.dao.WishlistDAO;
import com.gameshop.ecommerce.wishlist.model.WishList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WishlistService {
    private final WishlistDAO wishlistRepository;
    private final ProductDAO productDAO;

    public void addProductToWishlist(UUID productId, LocalUser user) {
        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));

        wishlistRepository.save(new WishList(Instant.now(), user, product));
    }

    @Transactional
    public void removeProductFromWishlist(UUID productId, LocalUser user) {

        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        wishlistRepository.deleteByProductAndLocalUser(product, user);
    }

    public List<WishList> getUserWishlist(LocalUser user) {

        return wishlistRepository.findByLocalUser(user);
    }

    public boolean isProductInWishlist(UUID productId, LocalUser user) {

        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        return wishlistRepository.findByLocalUser(user).stream().anyMatch(wishList -> wishList.getProduct().equals(product));
    }
}
