package com.gameshop.ecommerce.address.service;

import com.gameshop.ecommerce.address.store.AddressRepository;
import com.gameshop.ecommerce.address.store.AddressEntity;
import com.gameshop.ecommerce.user.store.LocalUserRepository;
import com.gameshop.ecommerce.user.store.LocalUserEntity;
import com.gameshop.ecommerce.address.store.AddressDto;
import com.gameshop.ecommerce.address.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.gameshop.ecommerce.exception.RequestException.badRequestException;
import static com.gameshop.ecommerce.exception.RequestException.notFoundRequestException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final LocalUserRepository localUserRepository;

    @Override
    public AddressDto createAddress(final AddressDto addressDto, final Long userId) {
        final var user = localUserRepository.findById(userId)
                .orElseThrow(() -> notFoundRequestException("User not found"));

        final var address = AddressEntity.builder()
                .firstName(addressDto.firstName())
                .lastName(addressDto.lastName())
                .contactNumber(addressDto.contactNumber())
                .country(addressDto.country())
                .city(addressDto.city())
                .addressLine(addressDto.addressLine())
                .postcode(addressDto.postcode())
                .user(user)
                .build();

        log.info("Creating new address: {}", addressDto);

        return addressMapper.addressToAddressDto(addressRepository.save(address));
    }

    @Override
    public AddressDto getAddress(Long userId, UUID addressId) {
        final var address = addressRepository.findById(addressId)
                .orElseThrow(() -> badRequestException("Address not found"));
        if (Objects.equals(address.getUser().getId(), userId)) {
            return addressBuilder(address);
        } else {
            throw badRequestException("Address not found");
        }
    }

    @Override
    public List<AddressDto> getAllAddresses(Long userId) {
        LocalUserEntity user = localUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return addressMapper.addressesToAddressDtos(user.getAddressEntities());
    }

    @Override
    public AddressDto updateAddress(Long userId, UUID addressId, AddressDto addressDto) {
        final var address = addressRepository.findById(addressId)
                .orElseThrow(() -> badRequestException("Address not found"));

        if (Objects.equals(address.getUser().getId(), userId)) {
            address.setFirstName(addressDto.firstName());
            address.setLastName(addressDto.lastName());
            address.setContactNumber(addressDto.contactNumber());
            address.setCountry(addressDto.country());
            address.setCity(addressDto.city());
            address.setAddressLine(addressDto.addressLine());
            address.setPostcode(addressDto.postcode());
        }

        return addressMapper.addressToAddressDto(addressRepository.save(address));
    }

    @Override
    public void deleteAddress(UUID addressId) {
        addressRepository.deleteById(addressId);
    }

    public AddressDto addressBuilder(AddressEntity addressEntity) {
        return AddressDto.builder()
                .firstName(addressEntity.getFirstName())
                .lastName(addressEntity.getLastName())
                .contactNumber(addressEntity.getContactNumber())
                .country(addressEntity.getCountry())
                .city(addressEntity.getCity())
                .addressLine(addressEntity.getAddressLine())
                .postcode(addressEntity.getPostcode())
                .build();
    }
}