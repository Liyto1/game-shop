package com.gameshop.ecommerce.address.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {
    Optional<AddressEntity> findById(UUID id);
    void deleteById(UUID id);
}