package com.yougrocery.yougrocery.authentication.services;

import com.yougrocery.yougrocery.authentication.dtos.AuthenticationResponseDTO;
import com.yougrocery.yougrocery.authentication.dtos.facebook.FacebookLoginRequestDTO;
import com.yougrocery.yougrocery.authentication.dtos.facebook.FacebookUserResponseDTO;
import com.yougrocery.yougrocery.authentication.exceptions.InternalServerException;
import com.yougrocery.yougrocery.authentication.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FacebookAuthenticationService {
    private final FacebookRestService fbRestService;
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordGenerator passwordGenerator;

    public AuthenticationResponseDTO loginOrCreateUser(FacebookLoginRequestDTO authRequest) {
        FacebookUserResponseDTO facebookUser = getRestUser(authRequest);

        return findUser(facebookUser)
                .or(() -> Optional.ofNullable(registerUser(facebookUser)))
                .map(jwtService::generateToken)
                .orElseThrow(() ->
                        new InternalServerException("Unable to login facebook user id " + facebookUser.id()))
                .transform(AuthenticationResponseDTO::new);
    }

    private FacebookUserResponseDTO getRestUser(FacebookLoginRequestDTO authRequest) {
        return fbRestService.getUser(authRequest.accessToken());
    }

    private Optional<User> findUser(FacebookUserResponseDTO facebookUser) {
        return userService.findById(Integer.parseInt(facebookUser.id()));
    }

    private User registerUser(FacebookUserResponseDTO facebookUser) {
        return userService.registerUser(
                facebookUser.convertToUser(
                        passwordGenerator.generatePassword(8)));
    }

}

