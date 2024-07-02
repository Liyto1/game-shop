package com.gameshop.www.eCommerce.cart.service;

import com.gameshop.www.eCommerce.cart.dao.CartDAO;
import com.gameshop.www.eCommerce.cart.model.Cart;
import com.gameshop.www.eCommerce.cart.model.CartDto;
import com.gameshop.www.eCommerce.cart.model.CartItem;
import com.gameshop.www.eCommerce.product.dao.ProductDAO;
import com.gameshop.www.eCommerce.product.model.Product;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartDAO cartDAO;
    private final ProductDAO productDAO;

    public CartService(CartDAO cartDAO, ProductDAO productDAO) {
        this.cartDAO = cartDAO;
        this.productDAO = productDAO;
    }

    public Cart getCartByUser(LocalUser user) {
        return cartDAO.findByUser(user).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            return cartDAO.save(cart);
        });
    }

    @Transactional
    public Cart addProductToCart(LocalUser user, List<CartDto> cartDtos) {
        Cart cart = getCartByUser(user);

        List<UUID> productIds = cartDtos.stream()
                .map(CartDto::getId)
                .collect(Collectors.toList());

        Map<UUID, Product> productMap = productDAO.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        Map<UUID, CartItem> cartItemMap = cart.getCartItems().stream()
                .collect(Collectors.toMap(cartItem -> cartItem.getProduct().getId(), cartItem -> cartItem));

        cartDtos.forEach(cartDto -> {
            Product product = productMap.get(cartDto.getId());
            if (product == null) {
                throw new RuntimeException("Product not found");
            }

            cartItemMap.computeIfPresent(product.getId(), (key, cartItem) -> {
                cartItem.setQuantity(cartItem.getQuantity() + cartDto.getQuantity());
                return cartItem;
            });

            cartItemMap.computeIfAbsent(product.getId(), key -> {
                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(cartDto.getQuantity());
                cartItem.setCart(cart);
                cart.getCartItems().add(cartItem);
                return cartItem;
            });
        });

        return cartDAO.save(cart);
    }

    @Transactional
    public Cart removeProductFromCart(LocalUser user, List<CartDto> cartDtos) {
        Cart cart = getCartByUser(user);

        Map<UUID, CartItem> cartItemMap = cart.getCartItems().stream()
                .collect(Collectors.toMap(cartItem -> cartItem.getProduct().getId(), cartItem -> cartItem));

        cartDtos.forEach(cartDto -> {
            CartItem cartItem = cartItemMap.get(cartDto.getId());
            if (cartItem != null) {
                int newQuantity = cartItem.getQuantity() - cartDto.getQuantity();
                if (newQuantity > 0) {
                    cartItem.setQuantity(newQuantity);
                } else {
                    cart.getCartItems().remove(cartItem);
                }
            }
        });

        return cartDAO.save(cart);
    }

    @Transactional
    public Cart clearCart(LocalUser user) {
        Cart cart = getCartByUser(user);
        cart.getCartItems().clear();
        return cartDAO.save(cart);
    }

    public Cart getCartDetails(LocalUser user) {
        return getCartByUser(user);
    }
}
