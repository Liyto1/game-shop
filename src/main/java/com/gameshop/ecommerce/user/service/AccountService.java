package com.gameshop.ecommerce.user.service;

import com.gameshop.ecommerce.security.EncryptionService;
import com.gameshop.ecommerce.user.dao.LocalUserDAO;
import com.gameshop.ecommerce.user.mapper.LocalUserMapper;
import com.gameshop.ecommerce.user.model.LocalUser;
import com.gameshop.ecommerce.user.model.LocalUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    private final LocalUserDAO localUserDAO;

    private final LocalUserMapper localUserMapper;

    private final EncryptionService encryptionService;


    public LocalUserDto updateInfo(LocalUser user, LocalUserDto userDto) {
        if (userDto.getNewPassword() != null) {
            updatePassword(user, userDto);
        }
        updateUserFields(user, userDto);

        return localUserMapper.entityToDto(localUserDAO.save(user));
    }

    private void updateUserFields(LocalUser user, LocalUserDto userDto) {
        Map<Consumer<String>, String> updates = new HashMap<>();
        updates.put(user::setFirstName, userDto.getFirstName());
        updates.put(user::setLastName, userDto.getLastName());
        updates.put(user::setEmail, userDto.getEmail());
        updates.put(user::setPhoneNumber, userDto.getPhoneNumber());

        updates.forEach((setter, value) -> {
            if (value != null && !value.isEmpty()) {
                setter.accept(value);
            }
        });
    }

    private void updatePassword(LocalUser user, LocalUserDto userDto) {
        if (encryptionService.checkPassword(userDto.getOldPassword(), user.getPassword())) {
            user.setPassword(encryptionService.encryptPassword(userDto.getNewPassword()));
        } else {
            log.error("Wrong old password");
            throw new IllegalArgumentException("Wrong old password");
        }
    }

}
