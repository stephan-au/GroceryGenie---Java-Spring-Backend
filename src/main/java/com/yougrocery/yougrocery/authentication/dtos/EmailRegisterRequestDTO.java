package com.yougrocery.yougrocery.authentication.dtos;

public record EmailRegisterRequestDTO(
        String firstName,
        String lastName,
        String email,
        String password) {
}
