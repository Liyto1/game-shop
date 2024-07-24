package com.gameshop.ecommerce.wishlist.store;

import com.gameshop.ecommerce.product.model.Product;
import com.gameshop.ecommerce.user.store.LocalUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WishlistRepository extends JpaRepository<WishListEntity, UUID> {
    void deleteByProductAndLocalUser(Product product, LocalUserEntity localUserEntity);
    List<WishListEntity> findByLocalUser(LocalUserEntity localUserEntity);
}