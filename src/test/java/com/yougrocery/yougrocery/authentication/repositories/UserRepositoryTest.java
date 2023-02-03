package com.yougrocery.yougrocery.authentication.repositories;

import com.yougrocery.yougrocery.authentication.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepo;

    @Test
    void getUserByEmail() {
        User expected = new User("test@email.com");
        userRepo.save(expected);

        User actual = userRepo.findByEmail("test@email.com").orElseThrow();

        assertEquals(expected, actual);
    }
}