package com.gameshop.ecommerce.auth.dao;

import com.gameshop.ecommerce.auth.model.VerificationToken;
import com.gameshop.ecommerce.user.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VerificationTokenDAO extends JpaRepository<VerificationToken, UUID> {

    Optional<VerificationToken> findByToken(String token);

    void deleteByUser(LocalUser user);
}
