package com.gameshop.ecommerce.address.service;

import com.gameshop.ecommerce.address.dto.AddressDto;
import com.gameshop.ecommerce.exception.AddressNotFoundException;
import com.gameshop.ecommerce.address.Address;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface AddressService {
    Address createAddress(AddressDto addressDto, UUID userId);

    AddressDto getAddress(UUID userId, UUID addressId) throws AddressNotFoundException;

    List<AddressDto> getAllAddresses(UUID userId);

    AddressDto updateAddress(UUID userId, UUID addressID, AddressDto addressDto);

    void deleteAddress(UUID addressId);

}
