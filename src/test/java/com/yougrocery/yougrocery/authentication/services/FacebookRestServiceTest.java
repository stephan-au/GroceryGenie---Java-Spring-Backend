package com.yougrocery.yougrocery.authentication.services;

import com.yougrocery.yougrocery.authentication.config.RestTemplateClient;
import com.yougrocery.yougrocery.authentication.dtos.facebook.FacebookPictureDataDTO;
import com.yougrocery.yougrocery.authentication.dtos.facebook.FacebookPictureResponseDTO;
import com.yougrocery.yougrocery.authentication.dtos.facebook.FacebookUserResponseDTO;
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
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {FacebookRestService.class})
@Import(RestTemplateClient.class)
@ImportAutoConfiguration(RestTemplateAutoConfiguration.class)
class FacebookRestServiceTest {

    @Autowired
    FacebookRestService fbRestService;

    @Test
    void shouldGetUserFromFacebook() {
        FacebookUserResponseDTO actualFbUser = fbRestService.getUser(
                "EAAH3gZCJlDNUBABYzqfZCNQZAINcmmamQZCVG0l1L3xQdKAaLVXODPJL2VRZBZCzeouwZANUEsILl3jVO3s80zzPIvZA8WqxTAPbayc99zbH5yx6KnEyrPGOCZAOl8iU2pRcNx6WecZCZBeZCZCTlqtd60Cp8pKYzEZCwlmZBHx4U6PMogyKPxLZARY8D9DvWT3u1F0ojbZAP6yzGsnRNCwZDZD");

        //Assert
        assertEquals("129344740037034", actualFbUser.id());
        assertEquals("open_yawohdl_user@tfbnw.net", actualFbUser.email());
        assertEquals("Open", actualFbUser.firstName());
        assertEquals("User", actualFbUser.lastName());
        assertEquals("180", actualFbUser.picture().data().width());
        assertEquals("180", actualFbUser.picture().data().height());
        assertEquals(
                "https://z-p3-scontent.fsdu4-1.fna.fbcdn.net/v/t1.30497-1/84628273_176159830277856_972693363922829312_n.jpg?stp=c212.0.720.720a_dst-jpg_p720x720&_nc_cat=1&ccb=1-7&_nc_sid=12b3be&_nc_eui2=AeFHQgi8997IoHRnmfG5-HlQik--Qfnh2B6KT75B-eHYHvFG7qTrxBDjqzSzZo_RqsG5DHPjRIX6Prbm0DsIQwSW&_nc_ohc=7z6O-rE62kgAX802r94&_nc_ht=z-p3-scontent.fsdu4-1.fna&edm=AP4hL3IEAAAA&oh=00_AfDCakFK9_2epHu4UP7A7b8FbxgvmpaSVutEQReoJlKrJQ&oe=640A8519",
                actualFbUser.picture().data().url());
    }
}

@ExtendWith(MockitoExtension.class)
class FacebookRestServiceMockTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    FacebookRestService fbRestService;

    @Test
    void shouldGetUserFromFacebook() {
        //Arrange
        final String accessToken = "some_access_token";

        FacebookUserResponseDTO expectedResponse = FacebookUserResponseDTO.builder()
                .id("12345")
                .email("stephan-0@hotmail.com")
                .firstName("Stephan")
                .lastName("Auwerda")
                .picture(new FacebookPictureResponseDTO(
                        new FacebookPictureDataDTO("720", "720", "https://photo@url.nl")))
                .build();

        when(restTemplate.getForObject(anyString(), eq(FacebookUserResponseDTO.class), anyMap()))
                .thenReturn(expectedResponse);

        var fields = "email,first_name,last_name,id,picture.width(720).height(720)";
        Map<String, String> variables = Map.of(
                "fields", fields,
                "redirect", "false",
                "access_token", accessToken);

        //Act
        FacebookUserResponseDTO actualResponse = fbRestService.getUser(accessToken);

        //Assert
        verify(restTemplate).getForObject(
                eq("https://graph.facebook.com/me?fields={fields}&redirect={redirect}&access_token={access_token}"),
                eq(FacebookUserResponseDTO.class),
                eq(variables));
        assertEquals(expectedResponse, actualResponse);
    }
}