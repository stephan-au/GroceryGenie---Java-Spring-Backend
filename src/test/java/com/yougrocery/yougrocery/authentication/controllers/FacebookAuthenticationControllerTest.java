package com.yougrocery.yougrocery.authentication.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yougrocery.yougrocery.authentication.config.SecurityConfiguration;
import com.yougrocery.yougrocery.authentication.dtos.AuthenticationResponseDTO;
import com.yougrocery.yougrocery.authentication.dtos.FacebookAuthenticationRequestDTO;
import com.yougrocery.yougrocery.authentication.services.FacebookAuthenticationService;
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

@WebMvcTest(FacebookAuthenticationController.class)
@Import(SecurityConfiguration.class)
class FacebookAuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserService userService;
    @MockBean
    FacebookAuthenticationService facebookAuthService;

    @Test
    void authenticate() throws Exception {
        var authenticationRequest = new FacebookAuthenticationRequestDTO("access_token");
        var authenticationResponse = new AuthenticationResponseDTO("test_token");

        when(facebookAuthService.authenticateOrCreateUser(any())).thenReturn(authenticationResponse);


        mockMvc.perform(post("/api/v1/auth/facebook/authenticate")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpectAll(
                        status().isOk(),
                        responseBody()
                                .containsObjectAsJson(
                                        authenticationResponse,
                                        AuthenticationResponseDTO.class));


        verify(facebookAuthService).authenticateOrCreateUser(authenticationRequest);
    }
}