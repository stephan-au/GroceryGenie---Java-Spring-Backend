package com.yougrocery.yougrocery.authentication.services;

import com.yougrocery.yougrocery.authentication.models.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    final User user = new User("test_user");

    @InjectMocks
    JwtService jwtService;

    @Test
    public void testExtractUsername() {
        String token = jwtService.generateToken(user);
        String username = jwtService.extractUsername(token);
        assertEquals("test_user", username);
    }

    @Test
    public void testExtractClaim() {
        String token = jwtService.generateToken(user);
        Date expiration = jwtService.extractClaim(token, Claims::getExpiration);
        assertFalse(expiration.before(new Date()));
    }

    @Test
    public void testGenerateToken() {
        String token = jwtService.generateToken(user);
        assertTrue(token.startsWith("eyJhbGciOiJIUzI1NiJ9"));
    }

    @Test
    public void testGenerateTokenWithExtraClaims() {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("extra_claim", "test_value");
        String token = jwtService.generateToken(extraClaims, user);
        String extraClaim = jwtService.extractClaim(token, claims -> (String) claims.get("extra_claim"));
        assertEquals("test_value", extraClaim);
    }

    @Test
    public void testIsTokenValid() {
        String token = jwtService.generateToken(user);
        assertTrue(jwtService.isTokenValid(token, user));
    }

    @Test
    public void isTokenInvalid() {
        String token = jwtService.generateToken(user);
        UserDetails invalidUserDetails = User.builder().email("Test@email.com").password("Password").build();
        boolean isValid = jwtService.isTokenValid(token, invalidUserDetails);
        assertFalse(isValid);
    }
}