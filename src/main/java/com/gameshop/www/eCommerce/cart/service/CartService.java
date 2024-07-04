package com.gameshop.www.eCommerce.cart.service;

import com.gameshop.www.eCommerce.cart.dao.CartDAO;
import com.gameshop.www.eCommerce.cart.model.Cart;
import com.gameshop.www.eCommerce.cart.model.CartBody;
import com.gameshop.www.eCommerce.cart.model.CartItem;
import com.gameshop.www.eCommerce.cart.model.dto.CartDto;
import com.gameshop.www.eCommerce.cart.model.dto.CartItemDto;
import com.gameshop.www.eCommerce.product.dao.ProductDAO;
import com.gameshop.www.eCommerce.product.dto.ProductCatalogDTO;
import com.gameshop.www.eCommerce.product.model.Product;
import com.gameshop.www.eCommerce.user.dao.LocalUserDAO;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
public class CartService {
    private final CartDAO cartDAO;
    private final ProductDAO productDAO;
    private final LocalUserDAO localUserDAO;


    public Cart getCartByUser(LocalUser user) {
        return cartDAO.findByUser(user).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            return cartDAO.save(cart);
        });
    }
//refactor to 1 request to db
    @Transactional
    public CartDto addProductToCart(LocalUser user, List<CartBody> cartBodies) {
        Cart cart = getCartByUser(user);

        for (CartBody cartBody : cartBodies) {
            Product product = productDAO.findById(cartBody.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

            Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                    .filter(cartItem -> cartItem.getProduct().equals(product))
                    .findFirst();

            if (existingCartItem.isPresent()) {
                CartItem cartItem = existingCartItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + cartBody.getProductQuantity());
            } else {
                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(cartBody.getProductQuantity());
                cartItem.setCart(cart);
                cart.getCartItems().add(cartItem);
            }
        }
        return convertToCartDto(cartDAO.save(cart));
    }

    @Transactional
    public CartDto removeProductFromCart(LocalUser user, List<CartBody> cartBodies) {
        Cart cart = getCartByUser(user);

        Map<UUID, CartItem> cartItemMap = cart.getCartItems().stream()
                .collect(Collectors.toMap(cartItem -> cartItem.getProduct().getId(), cartItem -> cartItem));

        cartBodies.forEach(cartBody -> {
            CartItem cartItem = cartItemMap.get(cartBody.getProductId());
            if (cartItem != null) {
                int newQuantity = cartItem.getQuantity() - cartBody.getProductQuantity();
                if (newQuantity > 0) {
                    cartItem.setQuantity(newQuantity);
                } else {
                    cart.getCartItems().remove(cartItem);
                }
            }
        });

        return convertToCartDto(cartDAO.save(cart));
    }

    @Transactional
    public Cart clearCart(LocalUser user) {
        Cart cart = getCartByUser(user);
        cart.getCartItems().clear();
        return cartDAO.save(cart);
    }

    public CartDto getCartDetails(LocalUser user) {
        return convertToCartDto(getCartByUser(user));
    }

    private CartDto convertToCartDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        Set<CartItemDto> cartItemsDTO = cart.getCartItems().stream()
                .map(cartItem -> {
                    CartItemDto cartItemDto = new CartItemDto();
                    cartItemDto.setId(cartItem.getId());

                    ProductCatalogDTO productDto = new ProductCatalogDTO();
                    productDto.setId(cartItem.getProduct().getId());
                    productDto.setName(cartItem.getProduct().getName());
                    productDto.setPrice(cartItem.getProduct().getPrice());
                    productDto.setImageUrl(cartItem.getProduct().getImageUrl());
                    productDto.setPriceWithSale(cartItem.getProduct().getPriceWithSale());
                    //setColor
                    cartItemDto.setProduct(productDto);
                    cartItemDto.setQuantity(cartItem.getQuantity());

                    return cartItemDto;
                })
                .collect(Collectors.toSet());
        cartDto.setCartItems(cartItemsDTO);

        return cartDto;
    }
}
