package com.gameshop.www.eCommerce.user.service;

import com.gameshop.www.eCommerce.user.dao.LocalUserDAO;
import com.gameshop.www.eCommerce.user.mapper.LocalUserMapper;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import com.gameshop.www.eCommerce.user.model.LocalUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class MyAccountService {

    private final LocalUserDAO localUserDAO;

    private final LocalUserMapper localUserMapper;

    @Autowired
    public MyAccountService(LocalUserDAO localUserDAO, LocalUserMapper localUserMapper) {
        this.localUserDAO = localUserDAO;
        this.localUserMapper = localUserMapper;
    }

    public LocalUserDto updateInfo(UUID userId, LocalUserDto userDto){
        LocalUser user = localUserDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(user.getFirstName().equals(userDto.getFirstName())||
        user.getLastName().equals(userDto.getLastName())||
        user.getPhoneNumber().equals(userDto.getPhoneNumber())||
        user.getEmail().equals(userDto.getEmail())) {

            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setEmail(userDto.getEmail());

            LocalUser updatedUser = localUserDAO.save(user);
            log.info("Information about user was updated successfully");
            return localUserMapper.entityToDto(updatedUser);
        }
        else{
            log.info("No new information. Checking password");
            return updatePassword(user,userDto);
        }
    }

    public LocalUserDto updatePassword(LocalUser user, LocalUserDto userDto){


        return null;
    }

}
