package com.yougrocery.yougrocery.authentication.services;

import com.yougrocery.yougrocery.authentication.models.User;
import io.jsonwebtoken.Claims;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails userDetails;


    @Before
    public void setUp() {
        jwtService = new JwtService();
        userDetails = new UserDetails() {
            private static final long serialVersionUID = 1L;

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return "test_user";
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            // Add the other required methods
        };
    }

    @Test
    public void testExtractUsername() {
        String token = jwtService.generateToken(userDetails);
        String username = jwtService.extractUsername(token);
        assertEquals("test_user", username);
    }

    @Test
    public void testExtractClaim() {
        String token = jwtService.generateToken(userDetails);
        Date expiration = jwtService.extractClaim(token, Claims::getExpiration);
        assertFalse(expiration.before(new Date()));
    }

    @Test
    public void testGenerateToken() {
        String token = jwtService.generateToken(userDetails);
        assertTrue(token.startsWith("eyJhbGciOiJIUzI1NiJ9"));
    }

    @Test
    public void testGenerateTokenWithExtraClaims() {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("extra_claim", "test_value");
        String token = jwtService.generateToken(extraClaims, userDetails);
        String extraClaim = jwtService.extractClaim(token, claims -> (String) claims.get("extra_claim"));
        assertEquals("test_value", extraClaim);
    }

    @Test
    public void testIsTokenValid() {
        String token = jwtService.generateToken(userDetails);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    public void isTokenInvalid() {
        String token = jwtService.generateToken(userDetails);
        UserDetails invalidUserDetails = User.builder().email("Test@email.com").password("Password").build();
        boolean isValid = jwtService.isTokenValid(token, invalidUserDetails);
        assertFalse(isValid);
    }
}