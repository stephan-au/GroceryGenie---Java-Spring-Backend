package com.yougrocery.yougrocery.authentication.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yougrocery.yougrocery.authentication.config.SecurityConfiguration;
import com.yougrocery.yougrocery.authentication.dtos.AuthenticationRequest;
import com.yougrocery.yougrocery.authentication.dtos.AuthenticationResponse;
import com.yougrocery.yougrocery.authentication.dtos.RegisterRequest;
import com.yougrocery.yougrocery.authentication.services.AuthenticationService;
import com.yougrocery.yougrocery.authentication.services.JwtService;
import com.yougrocery.yougrocery.authentication.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static com.yougrocery.yougrocery.assertionhelpers.ResponseBodyMatchers.responseBody;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthenticationController.class)
@Import(SecurityConfiguration.class)
class AuthenticationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    JwtService jwtService;
    @MockBean
    UserService userService;
    @MockBean
    AuthenticationService authenticationService;

    @Test
    void register() throws Exception {
        var registerRequest = new RegisterRequest(
                "stephan", "auwerda", "stephan@hotmail.com", "test12345");
        var authenticationResponse = new AuthenticationResponse("test_token");

        when(authenticationService.register(any())).thenReturn(authenticationResponse);


        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpectAll(
                        status().isOk(),
                        responseBody()
                                .containsObjectAsJson(
                                        authenticationResponse,
                                        AuthenticationResponse.class));


        verify(authenticationService).register(registerRequest);
    }

    @Test
    void authenticate() throws Exception {
        var authenticationRequest = new AuthenticationRequest("stephan@hotmail.com", "password");
        var authenticationResponse = new AuthenticationResponse("test_token");

        when(authenticationService.authenticate(any())).thenReturn(authenticationResponse);


        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpectAll(
                        status().isOk(),
                        responseBody()
                                .containsObjectAsJson(
                                        authenticationResponse,
                                        AuthenticationResponse.class));


        verify(authenticationService).authenticate(authenticationRequest);
    }
}