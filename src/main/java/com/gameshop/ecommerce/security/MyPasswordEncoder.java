package com.gameshop.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyPasswordEncoder {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    //TODO: Костиль, який треба прибрати! Після того як переробити методи де він приймає свою роль
    public boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}