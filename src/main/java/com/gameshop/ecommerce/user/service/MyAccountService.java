package com.gameshop.ecommerce.user.service;

import com.gameshop.ecommerce.security.EncryptionService;
import com.gameshop.ecommerce.user.dao.LocalUserDAO;
import com.gameshop.ecommerce.user.model.LocalUser;
import com.gameshop.ecommerce.user.mapper.LocalUserMapper;
import com.gameshop.ecommerce.user.model.LocalUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class MyAccountService {

    private final LocalUserDAO localUserDAO;

    private final LocalUserMapper localUserMapper;

    private final EncryptionService encryptionService;


    public LocalUserDto updateInfo(LocalUser user, LocalUserDto userDto) throws Exception {
        if(userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }else {
            user.setFirstName(user.getFirstName());
        }
        if(userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }
        else {
            user.setLastName(user.getLastName());
        }
        if(userDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }else{
            user.setPhoneNumber(user.getPhoneNumber());
        }
        if(userDto.getEmail() != null){
            user.setEmail(userDto.getEmail());
        }else{
            user.setEmail(user.getEmail());
        }

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
