package com.gameshop.www.eCommerce.user.mapper;

import com.gameshop.www.eCommerce.user.model.LocalUser;
import com.gameshop.www.eCommerce.user.model.LocalUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LocalUserMapper {
    LocalUserMapper INSTANCE = Mappers.getMapper(LocalUserMapper.class);

    LocalUserDto entityToDto(LocalUser localUser);
    LocalUser dtoToEntity(LocalUserDto localUserDto);
}
