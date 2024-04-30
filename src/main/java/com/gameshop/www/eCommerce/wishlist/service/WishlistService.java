package com.gameshop.www.eCommerce.wishlist.service;

import com.gameshop.www.eCommerce.product.dao.ProductDAO;
import com.gameshop.www.eCommerce.product.model.Product;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import com.gameshop.www.eCommerce.wishlist.dao.WishlistDAO;
import com.gameshop.www.eCommerce.wishlist.model.WishList;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class WishlistService {

    private final WishlistDAO wishlistRepository;
    private final ProductDAO productDAO;

    public WishlistService(WishlistDAO wishlistRepository, ProductDAO productDAO) {
        this.wishlistRepository = wishlistRepository;
        this.productDAO = productDAO;
    }

    public void addProductToWishlist(UUID productId, LocalUser user) {
        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

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
