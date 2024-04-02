package com.gameshop.www.eCommerce.generator.service;

import com.gameshop.www.eCommerce.user.dao.LocalUserDAO;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserGeneratorService {
    List<LocalUser> users = new ArrayList<>();
    private LocalUserDAO localUserDAO;
    private final String[] photos = new String[] {"http://surl.li/sdtvj", "http://surl.li/sdtvh"};

    public UserGeneratorService(LocalUserDAO localUserDAO) {
        this.localUserDAO = localUserDAO;
    }

    public void generateUsers() {
        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            LocalUser user = createUser(faker);
            users.add(user);
        }
        localUserDAO.saveAll(users);
        users.clear();
    }

    private LocalUser createUser(Faker faker) {
        LocalUser user = new LocalUser();
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        user.setPhoneNumber(faker.phoneNumber().cellPhone());
        user.setIsEmailVerified(true);
        user.setUserPhoto(photos[faker.random().nextInt(0,1)]);
        return user;
    }
}
