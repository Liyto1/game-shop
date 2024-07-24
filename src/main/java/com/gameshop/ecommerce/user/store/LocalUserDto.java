package com.gameshop.ecommerce.user.store;

import lombok.Builder;

@Builder
public record LocalUserDto(String firstName,
                           String lastName,
                           String email,
                           String phone,
                           String password,
                           String newPassword) { }