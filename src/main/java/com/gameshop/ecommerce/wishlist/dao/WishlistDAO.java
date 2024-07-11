package com.gameshop.ecommerce.wishlist.dao;

import com.gameshop.ecommerce.product.model.Product;
import com.gameshop.ecommerce.user.model.LocalUser;
import com.gameshop.ecommerce.wishlist.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WishlistDAO extends JpaRepository<WishList, UUID> {


    void deleteByProductAndLocalUser(Product product, LocalUser localUser);

    List<WishList> findByLocalUser(LocalUser localUser);
}
