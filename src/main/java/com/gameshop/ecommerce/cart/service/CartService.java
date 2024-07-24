package com.gameshop.ecommerce.cart.service;

import com.gameshop.ecommerce.cart.store.CartEntity;
import com.gameshop.ecommerce.cart.store.dto.CartDto;
import com.gameshop.ecommerce.cart.store.dto.CartItemDto;
import com.gameshop.ecommerce.product.dto.ProductCatalogDTO;
import com.gameshop.ecommerce.user.store.LocalUserEntity;
import com.gameshop.ecommerce.cart.store.CartRepository;
import com.gameshop.ecommerce.cart.store.dto.CartBody;
import com.gameshop.ecommerce.cart.store.CartItemEntity;
import com.gameshop.ecommerce.product.dao.ProductDAO;
import com.gameshop.ecommerce.product.model.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.gameshop.ecommerce.exception.RequestException.badRequestException;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductDAO productDAO;

    public CartEntity getCartByUser(LocalUserEntity user) {
        return cartRepository.findByUserId(user.getId()).orElseGet(() -> {
            CartEntity cartEntity = new CartEntity();
            cartEntity.setUser(user);
            return cartRepository.save(cartEntity);
        });
    }

    @Transactional
    public CartDto addProductToCart(LocalUserEntity user, List<CartBody> cartBodies) {
        CartEntity cartEntity = getCartByUser(user);

        List<Product> products = productDAO.findAllById(cartBodies.stream()
                .map(CartBody::id)
                .collect(Collectors.toList()));

        Map<UUID, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        for (CartBody cartBody : cartBodies) {
            Product product = productMap.get(cartBody.id());
            if (product == null) {
                throw badRequestException("Product not found with id: " + cartBody.id());
            }

            Optional<CartItemEntity> existingCartItem = cartEntity.getCartItemEntities().stream()
                    .filter(cartItem -> cartItem.getProduct().equals(product))
                    .findFirst();

            if (existingCartItem.isPresent()) {
                CartItemEntity cartItemEntity = existingCartItem.get();
                cartItemEntity.setQuantity(cartItemEntity.getQuantity() + cartBody.quantity());
            } else {
                CartItemEntity cartItemEntity = new CartItemEntity();
                cartItemEntity.setProduct(product);
                cartItemEntity.setQuantity(cartBody.quantity());
                cartItemEntity.setCartEntity(cartEntity);
                cartEntity.getCartItemEntities().add(cartItemEntity);
            }
        }
        return convertToCartDto(cartRepository.save(cartEntity));
    }

    @Transactional
    public CartEntity removeProductFromCart(LocalUserEntity user, List<CartBody> cartBodies) {
        CartEntity cartEntity = getCartByUser(user);

        Map<UUID, CartItemEntity> cartItemMap = cartEntity.getCartItemEntities().stream()
                .collect(Collectors.toMap(cartItem -> cartItem.getProduct().getId(), cartItem -> cartItem));

        cartBodies.forEach(cartBody -> {
            CartItemEntity cartItemEntity = cartItemMap.get(cartBody.id());
            if (cartItemEntity != null) {
                int newQuantity = cartItemEntity.getQuantity() - cartBody.quantity();
                if (newQuantity > 0) {
                    cartItemEntity.setQuantity(newQuantity);
                } else {
                    cartEntity.getCartItemEntities().remove(cartItemEntity);
                }
            }
        });

        return cartRepository.save(cartEntity);
    }

    @Transactional
    public CartEntity clearCart(LocalUserEntity user) {
        CartEntity cartEntity = getCartByUser(user);
        cartEntity.getCartItemEntities().clear();
        return cartRepository.save(cartEntity);
    }

    public CartDto getCartDetails(LocalUserEntity user) {
        return convertToCartDto(getCartByUser(user));
    }

    private CartDto convertToCartDto(CartEntity cartEntity) {
        return new CartDto(cartEntity.getCartItemEntities().stream()
                .map(this::createCartItemDto)
                .collect(Collectors.toSet()), cartEntity.getId());
    }

    private CartItemDto createCartItemDto(CartItemEntity cartItemEntity) {
        final var productDto = ProductCatalogDTO.builder()
                .id(cartItemEntity.getProduct().getId())
                .name(cartItemEntity.getProduct().getName())
                .imageUrl(cartItemEntity.getProduct().getImageUrl())
                .price(cartItemEntity.getProduct().getPrice())
                .priceWithSale(cartItemEntity.getProduct().getPriceWithSale())
                .build();

        return new CartItemDto(cartItemEntity.getId(), productDto, cartItemEntity.getQuantity());
    }
}