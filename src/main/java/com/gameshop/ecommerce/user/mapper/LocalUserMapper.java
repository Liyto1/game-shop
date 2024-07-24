package com.gameshop.ecommerce.user.mapper;

import com.gameshop.ecommerce.user.store.LocalUserDto;
import com.gameshop.ecommerce.user.store.LocalUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocalUserMapper {
    @Mapping(target = "newPassword", source = "password")
    LocalUserDto entityToDto(LocalUserEntity localUserEntity);
}