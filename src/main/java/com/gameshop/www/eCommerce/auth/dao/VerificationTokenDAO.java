package com.gameshop.www.eCommerce.auth.dao;

import com.gameshop.www.eCommerce.auth.model.VerificationToken;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VerificationTokenDAO extends JpaRepository<VerificationToken, UUID> {

    Optional<VerificationToken> findByToken(String token);

    void deleteByUser(LocalUser user);
}
