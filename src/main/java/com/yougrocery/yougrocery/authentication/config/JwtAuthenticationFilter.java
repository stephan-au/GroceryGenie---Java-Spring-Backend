package com.yougrocery.yougrocery.authentication.config;

import com.yougrocery.yougrocery.authentication.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        
        if (isInvalid(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(jwt);
        
        if (userEmail != null && isNotAuthenticatedYet()) {
            final UserDetails userDetails = getUserDetails(userEmail);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                authenticateUser(request, userDetails);
            }
        }
        
        filterChain.doFilter(request, response);
    }

    private static boolean isInvalid(String authHeader) {
        return authHeader == null || !authHeader.startsWith("Bearer ");
    }

    private static boolean isNotAuthenticatedYet() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private UserDetails getUserDetails(String userEmail) {
        return this.userDetailsService.loadUserByUsername(userEmail);
    }

    private static void authenticateUser(HttpServletRequest request, UserDetails userDetails) {
        var authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
