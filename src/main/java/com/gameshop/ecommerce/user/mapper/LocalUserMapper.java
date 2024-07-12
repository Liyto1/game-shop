package com.gameshop.ecommerce.user.mapper;

import com.gameshop.ecommerce.user.model.LocalUser;
import com.gameshop.ecommerce.user.model.LocalUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LocalUserMapper {
    LocalUserMapper INSTANCE = Mappers.getMapper(LocalUserMapper.class);

    LocalUserDto entityToDto(LocalUser localUser);
    LocalUser dtoToEntity(LocalUserDto localUserDto);
}
