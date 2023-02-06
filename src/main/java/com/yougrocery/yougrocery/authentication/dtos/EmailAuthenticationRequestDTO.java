package com.yougrocery.yougrocery.authentication.dtos;

public record EmailAuthenticationRequestDTO(
        String email,
        String password) {
}
