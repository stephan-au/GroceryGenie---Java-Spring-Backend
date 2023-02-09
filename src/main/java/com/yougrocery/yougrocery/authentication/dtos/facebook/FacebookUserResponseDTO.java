package com.yougrocery.yougrocery.authentication.dtos.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yougrocery.yougrocery.authentication.models.Profile;
import com.yougrocery.yougrocery.authentication.models.Role;
import com.yougrocery.yougrocery.authentication.models.User;
import lombok.Builder;

@Builder
public record FacebookUserResponseDTO(
        String id,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        String email,
        FacebookPictureResponseDTO picture
) {
        public User convertToUser(String password) {
                return User.builder()
                        .id(Integer.parseInt(id))
                        .email(email)
                        .password(password)
                        .role(Role.FACEBOOK_USER)
                        .userProfile(Profile.builder()
                                .displayName(String
                                        .format("%s %s", firstName, lastName))
                                .profilePictureUrl(picture.data().url())
                                .build())
                        .build();
        }
}
