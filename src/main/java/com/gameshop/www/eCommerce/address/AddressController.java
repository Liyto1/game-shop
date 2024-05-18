package com.gameshop.www.eCommerce.address;

import com.gameshop.www.eCommerce.address.dto.AddressDto;
import com.gameshop.www.eCommerce.address.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
@Tag(name = "Address", description = "API to work with Address")
public class AddressController {
    private final AddressService addressService;
    @Operation(summary = "Create a new address")
    @PostMapping
    public ResponseEntity<AddressDto> createNewAddress(@RequestBody AddressDto addressDto) {
        AddressDto createdAddress = addressService.createNewAddress(addressDto);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    @Operation(summary = "Get an address by ID")
    @GetMapping("/get/{id}")
    public ResponseEntity<AddressDto> getAddress(@PathVariable UUID id) {
        AddressDto addressDto = addressService.getAddress(id);
        return ResponseEntity.ok(addressDto);
    }

    @Operation(summary = "Get all addresses")
    @GetMapping("/get/all")
    public ResponseEntity<List<AddressDto>> getAllAddresses() {
        List<AddressDto> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    @Operation(summary = "Update an address by ID")
    @PutMapping("/update/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable UUID id, @RequestBody AddressDto addressDto) {
        AddressDto updatedAddress = addressService.updateAddress(id, addressDto);
        return ResponseEntity.ok(updatedAddress);
    }

    @Operation(summary = "Delete an address by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
