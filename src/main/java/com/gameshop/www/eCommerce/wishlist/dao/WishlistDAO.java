package com.gameshop.www.eCommerce.wishlist.dao;

import com.gameshop.www.eCommerce.product.model.Product;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import com.gameshop.www.eCommerce.wishlist.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WishlistDAO extends JpaRepository<WishList, UUID> {


    List<WishList> findByLocalUser_Id(UUID id);

    void deleteByProductAndLocalUser(Product product, LocalUser localUser);
}
