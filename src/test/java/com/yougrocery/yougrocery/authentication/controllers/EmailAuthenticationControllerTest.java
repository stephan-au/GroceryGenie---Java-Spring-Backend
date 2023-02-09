package com.yougrocery.yougrocery.authentication.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yougrocery.yougrocery.authentication.config.EncryptionConfiguration;
import com.yougrocery.yougrocery.authentication.config.SecurityConfiguration;
import com.yougrocery.yougrocery.authentication.dtos.EmailAuthenticationRequestDTO;
import com.yougrocery.yougrocery.authentication.dtos.AuthenticationResponseDTO;
import com.yougrocery.yougrocery.authentication.dtos.EmailRegisterRequestDTO;
import com.yougrocery.yougrocery.authentication.services.EmailAuthenticationService;
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


@WebMvcTest(EmailAuthenticationController.class)
@Import({SecurityConfiguration.class, EncryptionConfiguration.class})
class EmailAuthenticationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    JwtService jwtService;
    @MockBean
    UserService userService;
    @MockBean
    EmailAuthenticationService emailAuthenticationService;

    @Test
    void register() throws Exception {
        var registerRequest = new EmailRegisterRequestDTO(
                "stephan", "auwerda", "stephan@hotmail.com", "test12345");
        var authenticationResponse = new AuthenticationResponseDTO("test_token");

        when(emailAuthenticationService.register(any())).thenReturn(authenticationResponse);


        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpectAll(
                        status().isOk(),
                        responseBody()
                                .containsObjectAsJson(
                                        authenticationResponse,
                                        AuthenticationResponseDTO.class));


        verify(emailAuthenticationService).register(registerRequest);
    }

    @Test
    void authenticate() throws Exception {
        var authenticationRequest = new EmailAuthenticationRequestDTO("stephan@hotmail.com", "password");
        var authenticationResponse = new AuthenticationResponseDTO("test_token");

        when(emailAuthenticationService.authenticate(any())).thenReturn(authenticationResponse);


        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpectAll(
                        status().isOk(),
                        responseBody()
                                .containsObjectAsJson(
                                        authenticationResponse,
                                        AuthenticationResponseDTO.class));


        verify(emailAuthenticationService).authenticate(authenticationRequest);
    }
}