package com.gameshop.ecommerce.auth.repository;

import com.gameshop.ecommerce.auth.models.VerificationToken;
import com.gameshop.ecommerce.user.store.LocalUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {
    Optional<VerificationToken> findByToken(String token);
    void deleteByUser(LocalUserEntity user);
}