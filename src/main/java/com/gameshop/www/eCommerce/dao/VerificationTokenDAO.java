package com.gameshop.www.eCommerce.dao;

import com.gameshop.www.eCommerce.model.LocalUser;
import com.gameshop.www.eCommerce.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VerificationTokenDAO extends JpaRepository<VerificationToken, UUID> {

    Optional<VerificationToken> findByToken(String token);

    void deleteByUser(LocalUser user);
}
