package com.gameshop.ecommerce.user.service;

import com.gameshop.ecommerce.security.MyPasswordEncoder;
import com.gameshop.ecommerce.user.store.LocalUserRepository;
import com.gameshop.ecommerce.user.mapper.LocalUserMapper;
import com.gameshop.ecommerce.user.store.LocalUserEntity;
import com.gameshop.ecommerce.user.store.LocalUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static com.gameshop.ecommerce.exception.RequestException.badRequestException;


@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    private final LocalUserRepository localUserRepository;
    private final MyPasswordEncoder passwordEncoder;
    private final LocalUserMapper localUserMapper;

    public LocalUserDto updateInfo(LocalUserEntity user, LocalUserDto userDto) {

        if (userDto.password() != null) {
            updatePassword(user, userDto);
        }

        updateUserFields(user, userDto);

        return localUserMapper.entityToDto(localUserRepository.save(user));
    }

    private void updateUserFields(LocalUserEntity user, LocalUserDto userDto) {
        Map<Consumer<String>, String> updates = new HashMap<>();

        updates.put(user::setFirstName, userDto.firstName());
        updates.put(user::setLastName, userDto.lastName());
        updates.put(user::setEmail, userDto.email());
        updates.put(user::setPhone, userDto.phone());

        updates.forEach((setter, value) -> {
            if (value != null && !value.isEmpty()) {
                setter.accept(value);
                }
            }
        );
    }

    private void updatePassword(LocalUserEntity user, LocalUserDto userDto) {
        if (passwordEncoder.checkPassword(userDto.password(), user.getPassword())) {
            user.setPassword(passwordEncoder.passwordEncoder().encode(userDto.newPassword()));
        } else {
            log.error("Wrong old password");
            throw badRequestException("Wrong old password");
        }
    }
}