package com.gameshop.www.eCommerce.dao;

import com.gameshop.www.eCommerce.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LocalUserDAO extends JpaRepository<LocalUser, UUID> {
    Optional<LocalUser> findByEmailIgnoreCase(String email);

    Optional<LocalUser> findByPhoneNumber(String phoneNumber);


}
