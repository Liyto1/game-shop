package com.gameshop.ecommerce.address.mapper;

import com.gameshop.ecommerce.address.store.AddressDto;
import com.gameshop.ecommerce.address.store.AddressEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto addressToAddressDto(AddressEntity addressEntity);
    List<AddressDto> addressesToAddressDtos(List<AddressEntity> allAddressEntities);
}