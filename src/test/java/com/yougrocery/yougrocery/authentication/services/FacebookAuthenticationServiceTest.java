package com.yougrocery.yougrocery.authentication.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.yougrocery.yougrocery.authentication.dtos.AuthenticationResponseDTO;
import com.yougrocery.yougrocery.authentication.dtos.facebook.FacebookLoginRequestDTO;
import com.yougrocery.yougrocery.authentication.dtos.facebook.FacebookPictureDataDTO;
import com.yougrocery.yougrocery.authentication.dtos.facebook.FacebookPictureResponseDTO;
import com.yougrocery.yougrocery.authentication.dtos.facebook.FacebookUserResponseDTO;
import com.yougrocery.yougrocery.authentication.models.Role;
import com.yougrocery.yougrocery.authentication.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacebookAuthenticationServiceMockedTest {

    @Mock
    FacebookRestService fbRestService;

    @Mock
    UserService userService;

    @Mock
    JwtService jwtService;

    @Mock
    PasswordGenerator passwordGenerator;

    @InjectMocks
    FacebookAuthenticationService fbAuthService;

    FacebookLoginRequestDTO authRequest = new FacebookLoginRequestDTO("access_token");
    FacebookUserResponseDTO fbUser = FacebookUserResponseDTO.builder()
            .id("12345")
            .email("stephan-0@hotmail.com")
            .firstName("Stephan")
            .lastName("Auwerda")
            .picture(new FacebookPictureResponseDTO(
                    new FacebookPictureDataDTO("720", "720", "https://photo@url.nl")))
            .build();

    @Test
    void deserializeFacebookAuthenticationTokenDTO() throws Exception {
        //Arrange
        final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        String userJson = """
                {
                  "email": "stephan-0@hotmail.com",
                  "first_name": "Stephan",
                  "last_name": "Auwerda",
                  "id": "12345",
                  "picture": {
                    "data": {
                      "height": 720,
                      "url": "https://photo@url.nl",
                      "width": 720
                    }
                  }
                }
                 """;

        //Act
        FacebookUserResponseDTO deserializedJson = mapper.readValue(userJson, FacebookUserResponseDTO.class);

        //Assert
        assertThat(deserializedJson).isEqualTo(fbUser);
    }

    @Test
    void whenFindUserReturnsUser_thenGenerateToken() {
        User user = new User();

        when(fbRestService.getUser(authRequest.accessToken()))
                .thenReturn(fbUser);

        when(userService.findById(Integer.parseInt(fbUser.id())))
                .thenReturn(Optional.of(user));

        when(jwtService.generateToken(user))
                .thenReturn("generated_token");

        AuthenticationResponseDTO response = fbAuthService.loginOrCreateUser(authRequest);

        assertNotNull(response);
        assertEquals("generated_token", response.token());

        verify(fbRestService).getUser(authRequest.accessToken());
        verify(userService).findById(Integer.parseInt(fbUser.id()));
        verify(jwtService).generateToken(user);
        verifyNoInteractions(passwordGenerator);
    }

    @Test
    void whenFindUserReturnsEmpty_thenRegisterUserAndGenerateToken() {
        // Arrange
        User registeredUser = User.builder().id(1).build();

        when(fbRestService.getUser(authRequest.accessToken())).thenReturn(fbUser);
        when(userService.findById(Integer.parseInt(fbUser.id()))).thenReturn(Optional.empty());
        when(passwordGenerator.generatePassword(8)).thenReturn("password");
        when(userService.registerUser(any())).thenReturn(registeredUser);
        when(jwtService.generateToken(registeredUser)).thenReturn("token");

        // Act
        AuthenticationResponseDTO responseDTO = fbAuthService.loginOrCreateUser(authRequest);

        // Assert
        assertEquals("token", responseDTO.token());

        verify(userService).registerUser(argThat(
                user -> user.getPassword().equals("password") &&
                        user.getEmail().equals(fbUser.email()) &&
                        user.getId() == Integer.parseInt(fbUser.id()) &&
                        user.getRole().equals(Role.FACEBOOK_USER) &&
                        user.getUserProfile().getDisplayName().equals(
                                String.format("%s %s", fbUser.firstName(), fbUser.lastName())) &&
                        user.getUserProfile().getProfilePictureUrl().equals(fbUser.picture().data().url())
        ));
    }
}
