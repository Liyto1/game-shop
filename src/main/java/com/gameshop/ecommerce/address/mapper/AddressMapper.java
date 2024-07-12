package com.gameshop.ecommerce.address.mapper;

import com.gameshop.ecommerce.address.dto.AddressDto;
import com.gameshop.ecommerce.address.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDto addressToAddressDto(Address address);

    Address addressDtoToAddress(AddressDto addressDto);

    List<AddressDto> addressesToAddressDtos(List<Address> allAddresses);

}
