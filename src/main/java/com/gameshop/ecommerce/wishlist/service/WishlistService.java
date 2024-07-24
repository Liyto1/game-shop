package com.gameshop.ecommerce.wishlist.service;

import com.gameshop.ecommerce.user.store.LocalUserEntity;
import com.gameshop.ecommerce.product.dao.ProductDAO;
import com.gameshop.ecommerce.wishlist.store.WishListEntity;
import com.gameshop.ecommerce.wishlist.store.WishlistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.gameshop.ecommerce.exception.RequestException.badRequestException;

@RequiredArgsConstructor
@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductDAO productDAO;

    public void addProductToWishlist(UUID productId, LocalUserEntity user) {
        final var product = productDAO.findById(productId).orElseThrow(
                () -> badRequestException("Product not found with id " + productId)
        );

        wishlistRepository.save(new WishListEntity(Instant.now(), user, product));
    }

    @Transactional
    public void removeProductFromWishlist(UUID productId, LocalUserEntity user) {
        final var product = productDAO.findById(productId).orElseThrow(
                () -> badRequestException("Product not found")
        );

        wishlistRepository.deleteByProductAndLocalUser(product, user);
    }

    public List<WishListEntity> getUserWishlist(LocalUserEntity user) {

        return wishlistRepository.findByLocalUser(user);
    }

    public boolean isProductInWishlist(UUID productId, LocalUserEntity user) {
        final var product = productDAO.findById(productId).orElseThrow(
                () -> badRequestException("Product not found")
        );

        return wishlistRepository.findByLocalUser(user).stream()
                .anyMatch(wishListEntity -> wishListEntity.getProduct().equals(product));
    }
}