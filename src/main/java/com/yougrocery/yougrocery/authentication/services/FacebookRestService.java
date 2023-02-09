package com.yougrocery.yougrocery.authentication.services;

import com.yougrocery.yougrocery.authentication.dtos.facebook.FacebookUserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
class FacebookRestService {

    private final RestTemplate restTemplate;
    private final String FACEBOOK_GRAPH_API_BASE = "https://graph.facebook.com";

    FacebookUserResponseDTO getUser(String accessToken) {
        var path = "/me?fields={fields}&redirect={redirect}&access_token={access_token}";
        var fields = "email,first_name,last_name,id,picture.width(720).height(720)";

        Map<String, String> variables = Map.of(
                "fields", fields,
                "redirect", "false",
                "access_token", accessToken);

        return restTemplate
                .getForObject(FACEBOOK_GRAPH_API_BASE + path, FacebookUserResponseDTO.class, variables);
    }
}
