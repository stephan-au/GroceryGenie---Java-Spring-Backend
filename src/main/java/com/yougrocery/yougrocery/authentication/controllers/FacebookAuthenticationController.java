package com.yougrocery.yougrocery.authentication.controllers;

import com.yougrocery.yougrocery.authentication.dtos.AuthenticationResponse;
import com.yougrocery.yougrocery.authentication.dtos.FacebookAuthenticationRequest;
import com.yougrocery.yougrocery.authentication.services.FacebookAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/facebook")
@RequiredArgsConstructor
public class FacebookAuthenticationController {

    private final FacebookAuthenticationService facebookAuthService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateOrCreateUser(@RequestBody FacebookAuthenticationRequest accessToken) {
        return ResponseEntity.ok(facebookAuthService.authenticateOrCreateUser(accessToken));
    }
}
