package com.gameshop.ecommerce.user.service;

import com.gameshop.ecommerce.address.Address;
import com.gameshop.ecommerce.address.dto.AddressDto;
import com.gameshop.ecommerce.security.EncryptionService;
import com.gameshop.ecommerce.user.dao.LocalUserDAO;
import com.gameshop.ecommerce.user.model.LocalUser;
import com.gameshop.ecommerce.user.mapper.LocalUserMapper;
import com.gameshop.ecommerce.user.model.LocalUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class MyAccountService {

    private final LocalUserDAO localUserDAO;

    private final LocalUserMapper localUserMapper;

    private final EncryptionService encryptionService;

    @Autowired
    public MyAccountService(LocalUserDAO localUserDAO, LocalUserMapper localUserMapper, EncryptionService encryptionService) {
        this.localUserDAO = localUserDAO;
        this.localUserMapper = localUserMapper;
        this.encryptionService = encryptionService;
    }

    public LocalUserDto getInfo(UUID userId){
        LocalUser user = localUserDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userBuilder(user);
    }

    public LocalUserDto updateInfo(UUID userId, LocalUserDto userDto){
        LocalUser user = localUserDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!user.getFirstName().equals(userDto.getFirstName())||
                !user.getLastName().equals(userDto.getLastName())||
                !user.getPhoneNumber().equals(userDto.getPhoneNumber())||
                !user.getEmail().equals(userDto.getEmail())) {
            if(userDto.getFirstName() != null){
                user.setFirstName(userDto.getFirstName());
            }else {
                user.setFirstName(user.getFirstName());
            }
            if(userDto.getLastName() != null){
                user.setLastName(userDto.getLastName());
            }else{
                user.setLastName(user.getLastName());
            }
            if(userDto.getPhoneNumber() != null){
                user.setPhoneNumber(userDto.getPhoneNumber());
            }else{
                user.setPhoneNumber(user.getPhoneNumber());
            }
            if(userDto.getEmail() != null){
                user.setEmail(userDto.getEmail());
            }else{
                user.setEmail(user.getEmail());
            }
            if(userDto.getNewPassword() == null){
                LocalUser updatedUser = localUserDAO.save(user);
                log.info("Information about user was updated successfully");
                return localUserMapper.entityToDto(updatedUser);
            } else{
                return updatePassword(user,userDto);
            }

        }
        else{
            user.setFirstName(user.getFirstName());
            user.setLastName(user.getLastName());
            user.setPhoneNumber(user.getPhoneNumber());
            user.setEmail(user.getEmail());
            if(userDto.getNewPassword() == null){
                LocalUser updatedUser = localUserDAO.save(user);
                return localUserMapper.entityToDto(updatedUser);
            }else{
                log.info("No new information. Checking password");
                return updatePassword(user,userDto);
            }
        }
    }

    public LocalUserDto updatePassword(LocalUser user, LocalUserDto userDto){
        if(encryptionService.checkPassword(userDto.getOldPassword(), user.getPassword())){
            user.setPassword(encryptionService.encryptPassword(userDto.getNewPassword()));
            LocalUser updatedUser = localUserDAO.save(user);
            return localUserMapper.entityToDto(updatedUser);
        }else{
            log.info("Old password is incorrect");
            LocalUser updatedUser = localUserDAO.save(user);
            return localUserMapper.entityToDto(updatedUser);
        }
    }
    public LocalUserDto userBuilder(LocalUser user) {
        return LocalUserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .build();
    }
}
