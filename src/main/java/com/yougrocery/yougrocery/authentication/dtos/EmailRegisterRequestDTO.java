package com.yougrocery.yougrocery.authentication.dtos;

import com.yougrocery.yougrocery.authentication.models.Profile;
import com.yougrocery.yougrocery.authentication.models.Role;
import com.yougrocery.yougrocery.authentication.models.User;

public record EmailRegisterRequestDTO(
        String firstName,
        String lastName,
        String email,
        String password) {

    public User convertToUser() {
        return User.builder()
                .email(email)
                .password(password)
                .role(Role.USER)
                .userProfile(Profile.builder()
                        .displayName(String
                                .format("%s %s", firstName, lastName))
                        .build())
                .build();
    }
}
