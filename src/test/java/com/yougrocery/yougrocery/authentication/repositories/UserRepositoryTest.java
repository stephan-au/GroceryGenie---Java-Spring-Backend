package com.yougrocery.yougrocery.authentication.repositories;

import com.yougrocery.yougrocery.authentication.models.Profile;
import com.yougrocery.yougrocery.authentication.models.Role;
import com.yougrocery.yougrocery.authentication.models.User;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.StringUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void whenValidUser_thenSaveSuccessful() {
        User user = User.builder()
                .email("test@email.com")
                .password("testPassword")
                .role(Role.USER)
                .userProfile(Profile.builder()
                        .displayName("Test User")
                        .profilePictureUrl("https://test-url.com")
                        .build())
                .build();

        userRepository.save(user);

        assertThat(userRepository.findByEmail("test@email.com")).get().isEqualTo(user);
    }

    //TODO id van Facebook moet ik opslaan en mag niet overlappen
    @Test
    public void whenValidFacebookUserWithId_thenSaveSuccessful() {
        User user = User.builder()
                .id(123)
                .email("test@email.com")
                .password("testPassword")
                .role(Role.FACEBOOK_USER)
                .userProfile(Profile.builder()
                        .displayName("Test User")
                        .profilePictureUrl("https://test-url.com")
                        .build())
                .build();

        userRepository.save(user);

        assertThat(userRepository.findByEmail("test@email.com")).get().isEqualTo(user);
    }

    @Test
    public void whenEmailIsBlank_thenSaveFails() {
        User user = User.builder()
                .email("")
                .password("testPassword")
                .role(Role.USER)
                .build();

        assertThrows(
                ConstraintViolationException.class,
                () -> userRepository.saveAndFlush(user));
    }

    @Test
    public void whenPasswordIsBlank_thenSaveFails() {
        User user = User.builder()
                .email("test@email.com")
                .password("")
                .role(Role.FACEBOOK_USER)
                .build();

        assertThrows(
                ConstraintViolationException.class,
                () -> userRepository.saveAndFlush(user));
    }

    @Test
    public void whenEmailIsNotValid_thenSaveFails() {
        User user = User.builder()
                .email("invalidemail")
                .password("testPassword")
                .role(Role.ADMIN)
                .build();

        assertThrows(
                ConstraintViolationException.class,
                () -> userRepository.saveAndFlush(user));
    }

    @Test
    public void whenEmailLengthExceedsMax_thenSaveFails() {
        User user = User.builder()
                .email("a".repeat(95) + "@hotmail.com")
                .password("testPassword")
                .role(Role.USER)
                .build();

        assertThrows(
                ConstraintViolationException.class,
                () -> userRepository.saveAndFlush(user));
    }

    @Test
    public void whenPasswordLengthExceedsMax_thenSaveFails() {
        User user = User.builder()
                .email("test@email.com")
                .password("a".repeat(101))
                .role(Role.USER)
                .build();

        assertThrows(
                ConstraintViolationException.class,
                () -> userRepository.saveAndFlush(user));
    }

    @Test
    public void whenDisplayNameExceedsLength_thenSaveFails() {
        User user = User.builder()
                .email("test@email.com")
                .password("testPassword")
                .role(Role.USER)
                .userProfile(Profile.builder()
                        .displayName("a".repeat(102))
                        .profilePictureUrl("https://test-url.com")
                        .build())
                .build();

        assertThrows(
                ConstraintViolationException.class,
                () -> userRepository.saveAndFlush(user));
    }
}