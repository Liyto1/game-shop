package com.gameshop.www.eCommerce.address.service;

import com.gameshop.www.eCommerce.address.Address;
import com.gameshop.www.eCommerce.address.dto.AddressDto;
import com.gameshop.www.eCommerce.address.mapper.AddressMapper;
import com.gameshop.www.eCommerce.address.repository.AddressRepository;
import com.gameshop.www.eCommerce.exception.AddressNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public AddressDto createNewAddress(AddressDto addressDto) {
        Address address = addressRepository
                .save(addressMapper.addressDtoToAddress(addressDto));
        return addressMapper.addressToAddressDto(address);
    }

    @Override
    public AddressDto getAddress(UUID id) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address didn`t found with this id: " + id));
        return addressMapper.addressToAddressDto(address);
    }

    @Override
    public List<AddressDto> getAllAddresses() {

        List<Address> allAddresses = addressRepository.findAll();
        return addressMapper.addressesToAddressDtos(allAddresses);
    }

    @Override
    public AddressDto updateAddress(UUID id, AddressDto addressDto) {
        Address existingAddress = (Address) addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address didn`t found with this id: " + id));
        existingAddress.setFirstName(addressDto.getFirstName());
        existingAddress.setLastName(addressDto.getLastName());
        existingAddress.setContactNumber(addressDto.getContactNumber());
        existingAddress.setCountry(addressDto.getCountry());
        existingAddress.setCity(addressDto.getCity());
        existingAddress.setAddressLine(addressDto.getAddressLine());
        existingAddress.setPostcode(addressDto.getPostcode());
        Address updatedAddress = addressRepository.save(existingAddress);
        return addressMapper.addressToAddressDto(updatedAddress);
    }

    @Override
    public void deleteAddress(UUID id) {
        addressRepository.deleteById(id);
    }
}
