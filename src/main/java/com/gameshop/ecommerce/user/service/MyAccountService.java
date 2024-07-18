package com.gameshop.ecommerce.user.service;

import com.gameshop.ecommerce.address.Address;
import com.gameshop.ecommerce.address.dto.AddressDto;
import com.gameshop.ecommerce.exception.UserNotFoundException;
import com.gameshop.ecommerce.security.EncryptionService;
import com.gameshop.ecommerce.user.dao.LocalUserDAO;
import com.gameshop.ecommerce.user.model.LocalUser;
import com.gameshop.ecommerce.user.mapper.LocalUserMapper;
import com.gameshop.ecommerce.user.model.LocalUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyAccountService {

    private final LocalUserDAO localUserDAO;

    private final LocalUserMapper localUserMapper;

    private final EncryptionService encryptionService;


    public LocalUserDto updateInfo(LocalUser user, LocalUserDto userDto) throws Exception {
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(user.getEmail());
        if(userDto.getNewPassword() != null) {
            if (encryptionService.checkPassword(userDto.getOldPassword(), user.getPassword())) {
                user.setPassword(encryptionService.encryptPassword(userDto.getNewPassword()));
            } else {
                throw new Exception("Incorrect old password");
            }
        }
        LocalUser updatedUser = localUserDAO.save(user);
        return localUserMapper.entityToDto(updatedUser);
    }

}
