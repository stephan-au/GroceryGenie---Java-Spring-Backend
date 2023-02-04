package com.yougrocery.yougrocery.authentication.services;

import com.yougrocery.yougrocery.authentication.dtos.AuthenticationResponse;
import com.yougrocery.yougrocery.authentication.dtos.FacebookAccessTokenDTO;
import com.yougrocery.yougrocery.authentication.dtos.FacebookAuthenticationRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
@Transactional
public class FacebookAuthenticationService {

    private final RestTemplate restTemplate = new RestTemplate();;

    public AuthenticationResponse authenticateOrCreateUser(FacebookAuthenticationRequest authRequest) {
        String inspectAccessTokenUri = String.format(
                "graph.facebook.com/debug_token?input_token=%s&access_token=app-token",
                authRequest.accessToken());

        FacebookAccessTokenDTO response = restTemplate.getForObject(inspectAccessTokenUri, FacebookAccessTokenDTO.class);

        if (response == null) {

        }
        return null;
    }
}
