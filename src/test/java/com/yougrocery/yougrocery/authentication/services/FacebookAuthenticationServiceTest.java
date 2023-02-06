package com.yougrocery.yougrocery.authentication.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.yougrocery.yougrocery.authentication.config.RestTemplateClient;
import com.yougrocery.yougrocery.authentication.dtos.FacebookAccessTokenResponseDTO;
import com.yougrocery.yougrocery.authentication.dtos.FacebookAccessTokenResponseDataDTO;
import com.yougrocery.yougrocery.authentication.dtos.FacebookAuthenticationRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static com.yougrocery.yougrocery.authentication.services.FacebookAuthenticationService.APP_ID_ON_FACEBOOK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = FacebookAuthenticationService.class)
@Import(RestTemplateClient.class)
@ImportAutoConfiguration(RestTemplateAutoConfiguration.class)
class FacebookAuthenticationServiceTest {

    @Autowired
    FacebookAuthenticationService fbAuthService;

    @Test
    void deserializeFacebookAuthenticationTokenDTO() throws Exception {
        //Arrange
        final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        var expectedAccessToken = new FacebookAccessTokenResponseDTO(
                new FacebookAccessTokenResponseDataDTO(
                        138483919580948L,
                        "a10x",
                        true)
        );

        String accessTokenJson = """
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
        FacebookAccessTokenResponseDTO deserializedJson = mapper.readValue(accessTokenJson, FacebookAccessTokenResponseDTO.class);

        //Assert
        assertThat(deserializedJson).isEqualTo(expectedAccessToken);
    }

    @Test
    void authenticateValidAccessTokenOfNonExistingUser() {
        var authRequest = new FacebookAuthenticationRequestDTO(
                "EAAH3gZCJlDNUBAHsDDVsorYp0PU8v1XXiYRenfVQeuU3b8INHd6lywVE0oSwT0ZAZBazEfuAZBjZA6xkm1tcF4" +
                        "hyCkWCnnPsY9bu17IbuZCNmG6abQ1sOlMd9TuCpeg9xiPOGixmQ3DXrF4fpPYbgNwNMStpG0y7HlSlRibVPyJ14ZB" +
                        "8XvtFlDDRr3ZBGhKJQezJFqF2FAWaMNlCZCvTRQwwTVkWrZBZBKwwqMZCfU4fZCb9ysojQnDok8pUe5u8mWZAIsgV0ZD");

        assertDoesNotThrow(() -> fbAuthService.authenticateOrCreateUser(authRequest));
    }

    @Test
    void authenticateInvalidAccessToken() {
        var authRequest = new FacebookAuthenticationRequestDTO(
                "test12345");

        assertThrows(HttpClientErrorException.class, () -> fbAuthService.authenticateOrCreateUser(authRequest));
    }
}

@ExtendWith(MockitoExtension.class)
class FacebookAuthenticationServiceMockedTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    FacebookAuthenticationService fbAuthService;

    @Test
    void authenticateValidAccessTokenOfNonExistingUser() {
        //Arrange
        var expectedTokenResponse = new FacebookAccessTokenResponseDTO(
                new FacebookAccessTokenResponseDataDTO(APP_ID_ON_FACEBOOK, "1234567890", true));

        when(restTemplate.getForObject(any(String.class), eq(FacebookAccessTokenResponseDTO.class)))
                .thenReturn(expectedTokenResponse);

        var authRequest = new FacebookAuthenticationRequestDTO("accessTokenMock");


        //Act + assert
        assertDoesNotThrow(() -> fbAuthService.authenticateOrCreateUser(authRequest));
    }

    @Test
    void authenticateInvalidAppID() {
        //Arrange
        var expectedTokenResponse = new FacebookAccessTokenResponseDTO(
                new FacebookAccessTokenResponseDataDTO(1234L, "1234567890", true));

        when(restTemplate.getForObject(any(String.class), eq(FacebookAccessTokenResponseDTO.class)))
                .thenReturn(expectedTokenResponse);

        var authRequest = new FacebookAuthenticationRequestDTO("accessTokenMock");

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> fbAuthService.authenticateOrCreateUser(authRequest));
    }

    @Test
    void authenticateInvalidToken() {
        //Arrange
        var expectedTokenResponse = new FacebookAccessTokenResponseDTO(
                new FacebookAccessTokenResponseDataDTO(APP_ID_ON_FACEBOOK, "1234567890", false));

        when(restTemplate.getForObject(any(String.class), eq(FacebookAccessTokenResponseDTO.class)))
                .thenReturn(expectedTokenResponse);

        var authRequest = new FacebookAuthenticationRequestDTO("accessTokenMock");

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> fbAuthService.authenticateOrCreateUser(authRequest));
    }
}
