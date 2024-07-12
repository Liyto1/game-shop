package com.gameshop.ecommerce.user.dao;

import com.gameshop.ecommerce.user.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocalUserDAO extends JpaRepository<LocalUser, UUID> {
    Optional<LocalUser> findByEmailIgnoreCase(String email);

    Optional<LocalUser> findByPhoneNumber(String phoneNumber);


}
