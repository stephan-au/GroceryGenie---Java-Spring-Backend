package com.yougrocery.yougrocery.authentication.controllers;

import com.yougrocery.yougrocery.authentication.dtos.EmailAuthenticationRequestDTO;
import com.yougrocery.yougrocery.authentication.dtos.AuthenticationResponseDTO;
import com.yougrocery.yougrocery.authentication.dtos.EmailRegisterRequestDTO;
import com.yougrocery.yougrocery.authentication.services.EmailAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class EmailAuthenticationController {

    private final EmailAuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(
            @RequestBody EmailRegisterRequestDTO request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(
            @RequestBody EmailAuthenticationRequestDTO request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
