package com.gameshop.www.eCommerce.address.service;

import com.gameshop.www.eCommerce.address.Address;
import com.gameshop.www.eCommerce.address.dto.AddressDto;
import com.gameshop.www.eCommerce.address.mapper.AddressMapper;
import com.gameshop.www.eCommerce.address.repository.AddressRepository;
import com.gameshop.www.eCommerce.exception.AddressNotFoundException;
import com.gameshop.www.eCommerce.user.dao.LocalUserDAO;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    private final LocalUserDAO localUserDAO;

    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper,LocalUserDAO localUserDAO) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.localUserDAO = localUserDAO;
    }

    @Override
    public Address createAddress(AddressDto addressDto,UUID userId) {
        LocalUser user = localUserDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = new Address();
        address.setFirstName(addressDto.getFirstName());
        address.setLastName(addressDto.getLastName());
        address.setContactNumber(addressDto.getContactNumber());
        address.setCountry(addressDto.getCountry());
        address.setCity(addressDto.getCity());
        address.setAddressLine(addressDto.getAddressLine());
        address.setPostcode(addressDto.getPostcode());
        address.setUser(user);

        return addressRepository.save(address);
    }

    @Override
    public AddressDto getAddress(UUID userId, UUID addressId) {
        LocalUser user = localUserDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
        if(address.getUser().getId() == userId) {
            return  addressBuilder(address);
        }
        else{
            throw new AddressNotFoundException("Address not found");
        }
    }

    @Override
    public List<AddressDto> getAllAddresses(UUID userId) {
        LocalUser user = localUserDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return addressMapper.addressesToAddressDtos(user.getAddresses());
    }

    @Override
    public AddressDto updateAddress(UUID userId, UUID addressId, AddressDto addressDto) {
        LocalUser user = localUserDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));

        if(address.getUser().getId() == userId) {
            address.setFirstName(addressDto.getFirstName());
            address.setLastName(addressDto.getLastName());
            address.setContactNumber(addressDto.getContactNumber());
            address.setCountry(addressDto.getCountry());
            address.setCity(addressDto.getCity());
            address.setAddressLine(addressDto.getAddressLine());
            address.setPostcode(addressDto.getPostcode());
        }

        Address updatedAddress = addressRepository.save(address);
        return addressMapper.addressToAddressDto(updatedAddress);
    }

    @Override
    public void deleteAddress(UUID addressId) {
        addressRepository.deleteById(addressId);
    }

    public AddressDto addressBuilder(Address address){
        return AddressDto.builder()
                .firstName(address.getFirstName())
                .lastName(address.getLastName())
                .contactNumber(address.getContactNumber())
                .country(address.getCountry())
                .city(address.getCity())
                .addressLine(address.getAddressLine())
                .postcode(address.getPostcode())
                .build();
    }
}
