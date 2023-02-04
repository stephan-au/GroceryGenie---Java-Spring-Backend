package com.yougrocery.yougrocery.authentication.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.yougrocery.yougrocery.authentication.dtos.FacebookAccessTokenDTO;
import com.yougrocery.yougrocery.authentication.dtos.FacebookAccessTokenDataDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FacebookAuthenticationServiceTest {

    @Test
    void deserializeFacebookAuthenticationTokenDTO() throws Exception {
        //Arrange
        final ObjectMapper mapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);

        FacebookAccessTokenDTO facebookAccessTokenDTO = new FacebookAccessTokenDTO(
                new FacebookAccessTokenDataDTO(
                        138483919580948L,
                        "a10x",
                        true)
        );

        var json = """
                {
                    "data": {
                        "app_id": 138483919580948,
                        "type": "USER",
                        "application": "Social Cafe",
                        "expires_at": 1352419328,
                        "is_valid": true,
                        "issued_at": 1347235328,
                        "metadata": {
                            "sso": "iphone-safari"
                        },
                        "scopes": [
                            "email",
                            "publish_actions"
                        ],
                        "user_id": "a10x"
                    }
                }
                 """;

        //Act
        var deserialized = mapper.readValue(json, FacebookAccessTokenDTO.class);

        //Assert
        assertThat(deserialized).isEqualTo(facebookAccessTokenDTO);
    }
}