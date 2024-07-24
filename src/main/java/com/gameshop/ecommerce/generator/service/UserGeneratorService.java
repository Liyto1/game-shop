package com.gameshop.ecommerce.generator.service;

import com.gameshop.ecommerce.user.store.LocalUserRepository;
import com.gameshop.ecommerce.user.store.LocalUserEntity;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserGeneratorService {
    private static final String USER_PHOTO = "https://girlsonlytravel.com/img/works/user.jfif";
    private final LocalUserRepository localUserRepository;

    public void generateUsers() {
        List<LocalUserEntity> users = new ArrayList<>();
        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            LocalUserEntity user = createUser(faker);
            users.add(user);
        }
        localUserRepository.saveAll(users);
    }

    private LocalUserEntity createUser(Faker faker) {
        LocalUserEntity user = new LocalUserEntity();
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        user.setIsEmailVerified(true);
        user.setAuthType("local");
        user.setUserPhoto(USER_PHOTO);
        return user;
    }
}
