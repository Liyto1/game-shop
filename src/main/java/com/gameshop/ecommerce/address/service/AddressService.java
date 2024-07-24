package com.gameshop.ecommerce.address.service;

import com.gameshop.ecommerce.address.store.AddressDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface AddressService {
    AddressDto createAddress(AddressDto addressDto, Long userId);
    AddressDto getAddress(Long userId, UUID addressId);
    List<AddressDto> getAllAddresses(Long userId);
    AddressDto updateAddress(Long userId, UUID addressID, AddressDto addressDto);
    void deleteAddress(UUID addressId);
}