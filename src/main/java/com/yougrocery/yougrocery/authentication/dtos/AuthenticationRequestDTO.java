package com.yougrocery.yougrocery.authentication.dtos;

public record AuthenticationRequestDTO(
        String email,
        String password) {
}
