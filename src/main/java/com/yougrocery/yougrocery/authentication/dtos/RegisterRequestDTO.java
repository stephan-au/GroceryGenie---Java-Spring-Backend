package com.yougrocery.yougrocery.authentication.dtos;

public record RegisterRequestDTO(
        String firstName,
        String lastName,
        String email,
        String password) {
}
