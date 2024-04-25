package com.gameshop.www.eCommerce.wishlist.service;

import com.gameshop.www.eCommerce.product.dao.ProductDAO;
import com.gameshop.www.eCommerce.product.model.Product;
import com.gameshop.www.eCommerce.user.dao.LocalUserDAO;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import com.gameshop.www.eCommerce.wishlist.dao.WishlistDAO;
import com.gameshop.www.eCommerce.wishlist.model.WishList;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WishlistService {

    private final WishlistDAO wishlistRepository;
    private final LocalUserDAO localUserDAO;
    private final ProductDAO productDAO;

    public WishlistService(WishlistDAO wishlistRepository, LocalUserDAO localUserDAO, ProductDAO productDAO) {
        this.wishlistRepository = wishlistRepository;
        this.localUserDAO = localUserDAO;
        this.productDAO = productDAO;
    }

    public void addProductToWishlist(UUID productId, UUID userId) throws IllegalArgumentException {
        Optional<LocalUser> user = localUserDAO.findById(userId);
        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));


        wishlistRepository.save(new WishList(product, Instant.now(), user.get()));
    }

    @Transactional
    public void removeProductFromWishlist(UUID productId, UUID userId) {

        LocalUser user = localUserDAO.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        wishlistRepository.deleteByProductAndLocalUser(product, user);
    }

    public List<WishList> getWishlist(UUID userId) {

        return wishlistRepository.findByLocalUser_Id(userId);
    }

    public boolean isProductInWishlist(UUID productId, UUID userId) {

        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        List<WishList> userWishlist = wishlistRepository.findByLocalUser_Id(userId);

        return userWishlist.stream().anyMatch(wishList -> wishList.getProduct().equals(product));
    }
}
