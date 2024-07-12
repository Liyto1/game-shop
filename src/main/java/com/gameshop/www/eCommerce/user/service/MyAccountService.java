package com.gameshop.www.eCommerce.user.service;

import com.gameshop.www.eCommerce.user.dao.LocalUserDAO;
import com.gameshop.www.eCommerce.user.mapper.LocalUserMapper;
import com.gameshop.www.eCommerce.user.model.LocalUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyAccountService {

    private final LocalUserDAO localUserDAO;

    private final LocalUserMapper localUserMapper;

    @Autowired
    public MyAccountService(LocalUserDAO localUserDAO, LocalUserMapper localUserMapper) {
        this.localUserDAO = localUserDAO;
        this.localUserMapper = localUserMapper;
    }

    public LocalUserDto updateInfo( LocalUserDto localUserDto){
        return null;
    }

}
