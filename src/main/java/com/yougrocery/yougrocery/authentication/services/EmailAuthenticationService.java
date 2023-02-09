package com.yougrocery.yougrocery.authentication.services;

import com.yougrocery.yougrocery.authentication.dtos.AuthenticationResponseDTO;
import com.yougrocery.yougrocery.authentication.dtos.EmailAuthenticationRequestDTO;
import com.yougrocery.yougrocery.authentication.dtos.EmailRegisterRequestDTO;
import com.yougrocery.yougrocery.authentication.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailAuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDTO register(EmailRegisterRequestDTO request) {
        User user = request.convertToUser();

        userService.registerUser(user);

        return new AuthenticationResponseDTO(generateToken(user));
    }

    public AuthenticationResponseDTO authenticate(EmailAuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        UserDetails user = userService.loadUserByUsername(request.email());

        return new AuthenticationResponseDTO(generateToken(user));
    }

    private String generateToken(UserDetails user) {
        return jwtService.generateToken(user);
    }
}
