package com.gameshop.ecommerce.user.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalUserRepository extends JpaRepository<LocalUserEntity, Long> {
    Optional<LocalUserEntity> findByEmailIgnoreCase(String email);
    Optional<LocalUserEntity> findByPhone(String phone);
}