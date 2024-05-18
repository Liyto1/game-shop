package com.gameshop.www.eCommerce.address.mapper;

import com.gameshop.www.eCommerce.address.Address;
import com.gameshop.www.eCommerce.address.dto.AddressDto;
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
