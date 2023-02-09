package com.yougrocery.yougrocery.authentication.dtos.facebook;

import com.yougrocery.yougrocery.authentication.models.Role;
import com.yougrocery.yougrocery.authentication.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FacebookUserResponseDTOTest {

    @Test
    public void convertToUser_ValidInput_ReturnsUser() {
        FacebookUserResponseDTO facebookUserResponseDTO = FacebookUserResponseDTO.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .picture(new FacebookPictureResponseDTO(
                        new FacebookPictureDataDTO(
                                "720", "720", "https://example.com/profile_picture.jpg")))
                .build();

        //Act
        User user = facebookUserResponseDTO.convertToUser("password");

        //Assert
        assertEquals(1, user.getId());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals(Role.FACEBOOK_USER, user.getRole());
        assertEquals("John Doe", user.getUserProfile().getDisplayName());
        assertEquals(
                "https://example.com/profile_picture.jpg",
                user.getUserProfile().getProfilePictureUrl());
    }
}