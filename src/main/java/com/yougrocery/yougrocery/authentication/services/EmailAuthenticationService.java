package com.yougrocery.yougrocery.authentication.services;

import com.yougrocery.yougrocery.authentication.dtos.EmailAuthenticationRequestDTO;
import com.yougrocery.yougrocery.authentication.dtos.AuthenticationResponseDTO;
import com.yougrocery.yougrocery.authentication.dtos.EmailRegisterRequestDTO;
import com.yougrocery.yougrocery.authentication.models.Role;
import com.yougrocery.yougrocery.authentication.models.User;
import com.yougrocery.yougrocery.authentication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailAuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDTO register(EmailRegisterRequestDTO request) {
        var user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        repository.save(user);

        return new AuthenticationResponseDTO(generateToken(user));
    }

    public AuthenticationResponseDTO authenticate(EmailAuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var user = repository.findByEmail(request.email())
                .orElseThrow();

        return new AuthenticationResponseDTO(generateToken(user));
    }

    private String generateToken(User user) {
        return jwtService.generateToken(user);
    }
}
