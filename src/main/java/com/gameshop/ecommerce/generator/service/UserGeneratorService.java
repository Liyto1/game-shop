package com.gameshop.ecommerce.generator.service;

import com.gameshop.ecommerce.user.dao.LocalUserDAO;
import com.gameshop.ecommerce.user.model.LocalUser;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserGeneratorService {
    private static final String USER_PHOTO = "https://girlsonlytravel.com/img/works/user.jfif";
    private final LocalUserDAO localUserDAO;

    public void generateUsers() {
        List<LocalUser> users = new ArrayList<>();
        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            LocalUser user = createUser(faker);
            users.add(user);
        }
        localUserDAO.saveAll(users);
    }

    private LocalUser createUser(Faker faker) {
        LocalUser user = new LocalUser();
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        user.setIsEmailVerified(true);
        user.setAuthType("local");
        user.setUserPhoto(USER_PHOTO);
        return user;
    }
}
