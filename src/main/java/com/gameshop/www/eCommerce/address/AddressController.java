package com.gameshop.www.eCommerce.address;

import com.gameshop.www.eCommerce.address.dto.AddressDto;
import com.gameshop.www.eCommerce.address.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/addresses")
@RequiredArgsConstructor
@Tag(name = "Address", description = "API to work with Address")
public class AddressController {
    private final AddressService addressService;

    @Operation(summary = "Create a new address")
    @PostMapping
    public ResponseEntity<AddressDto> createNewAddress(@Valid @RequestBody AddressDto addressDto) {
        log.info("Creating new address: {}", addressDto);
        AddressDto createdAddress = addressService.createNewAddress(addressDto);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    @Operation(summary = "Get an address by ID")
    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddress(@PathVariable UUID id) {
        AddressDto addressDto = addressService.getAddress(id);
        return ResponseEntity.ok(addressDto);
    }

    @Operation(summary = "Get all addresses")
    @GetMapping()
    public ResponseEntity<List<AddressDto>> getAllAddresses() {
        List<AddressDto> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    @Operation(summary = "Update an address by ID")
    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable UUID id, @RequestBody AddressDto addressDto) {
        AddressDto updatedAddress = addressService.updateAddress(id, addressDto);
        return ResponseEntity.ok(updatedAddress);
    }

    @Operation(summary = "Delete an address by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
