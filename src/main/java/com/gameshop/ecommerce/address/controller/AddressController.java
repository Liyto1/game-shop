package com.gameshop.ecommerce.address.controller;

import com.gameshop.ecommerce.address.store.AddressDto;
import com.gameshop.ecommerce.address.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
@Tag(name = "Address", description = "API to work with Address")
public class AddressController {

    private final AddressService addressService;

    private static final String CREATE_ADDRESS_USERID = "/{userId}";
    private static final String GET_ADDRESS_USER_ID = "/{userId}/{addressId}";
    private static final String GETS_ALL_USERS_ID = "/{userId}";
    private static final String UPDATE_ADDRESS = "/{userId}/{addressId}";
    private static final String DELETE_ADDRESS_ID = "/{addressId}";

    @Operation(summary = "Create a new address")
    @PostMapping(CREATE_ADDRESS_USERID)
    public AddressDto createAddress(@Valid @PathVariable Long userId, @RequestBody AddressDto addressDto) {
        return addressService.createAddress(addressDto, userId);
    }

    @Operation(summary = "Get an address by ID")
    @GetMapping(GET_ADDRESS_USER_ID)
    public AddressDto getAddress(@PathVariable Long userId, @PathVariable UUID addressId) {
        return addressService.getAddress(userId, addressId);
    }

    @Operation(summary = "Get all addresses")
    @GetMapping(GETS_ALL_USERS_ID)
    public List<AddressDto> getAllAddresses(@PathVariable Long userId) {
        return addressService.getAllAddresses(userId);
    }

    @Operation(summary = "Update an address by ID")
    @PutMapping(UPDATE_ADDRESS)
    public AddressDto updateAddress(@PathVariable Long userId,
                                    @PathVariable UUID addressId,
                                    @RequestBody AddressDto addressDto) {
        return addressService.updateAddress(userId, addressId, addressDto);
    }

    @Operation(summary = "Delete an address by ID")
    @DeleteMapping(DELETE_ADDRESS_ID)
    public void deleteAddress(@PathVariable UUID addressId) {
        addressService.deleteAddress(addressId);
    }
}