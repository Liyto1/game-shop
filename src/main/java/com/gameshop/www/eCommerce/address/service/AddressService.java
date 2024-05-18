package com.gameshop.www.eCommerce.address.service;

import com.gameshop.www.eCommerce.address.dto.AddressDto;
import com.gameshop.www.eCommerce.exception.AddressNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface AddressService {
    AddressDto createNewAddress(AddressDto addressDto);
    AddressDto getAddress (UUID id) throws AddressNotFoundException;
    List<AddressDto> getAllAddresses();
    AddressDto updateAddress(UUID id,AddressDto addressDto);
    void deleteAddress(UUID id);

}
