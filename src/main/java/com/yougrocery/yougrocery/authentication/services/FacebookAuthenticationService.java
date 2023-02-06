package com.yougrocery.yougrocery.authentication.services;

import com.yougrocery.yougrocery.authentication.dtos.AuthenticationResponseDTO;
import com.yougrocery.yougrocery.authentication.dtos.FacebookAccessTokenResponseDTO;
import com.yougrocery.yougrocery.authentication.dtos.FacebookAuthenticationRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class FacebookAuthenticationService {

    final static long APP_ID_ON_FACEBOOK = 553621056195797L;
    private final RestTemplate restTemplate;

    public AuthenticationResponseDTO authenticateOrCreateUser(FacebookAuthenticationRequestDTO authRequest) {
        validateAccessToken(authRequest.accessToken());

        return null;
    }

    private void validateAccessToken(String accessToken) {
        FacebookAccessTokenResponseDTO response = restGetAccessToken(accessToken);

        if(!response.data().isValid()){
            throw new IllegalArgumentException("Access token not valid");
        }
        if(response.data().appId() != APP_ID_ON_FACEBOOK){
            throw new IllegalArgumentException("Access token not valid, wrong app id: " + response.data().appId());
        }
    }

    private FacebookAccessTokenResponseDTO restGetAccessToken(String accessToken) {
        String url = "https://graph.facebook.com/v16.0/debug_token?input_token=" + accessToken + "&access_token=" + accessToken ;

        return restTemplate.getForObject(
                url,
                FacebookAccessTokenResponseDTO.class);
    }
}

