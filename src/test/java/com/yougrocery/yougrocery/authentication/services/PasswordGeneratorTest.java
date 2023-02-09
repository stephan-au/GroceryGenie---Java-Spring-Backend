package com.yougrocery.yougrocery.authentication.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordGeneratorTest {
    private final PasswordGenerator passwordGenerator = new PasswordGenerator();

    @Test
    public void generatePassword_LengthIsValid_ReturnsPassword() {
        String password = passwordGenerator.generatePassword(8);
        assertEquals(8, password.length());
    }

    @Test
    public void generatePassword_ContainsLowerCase_ReturnsTrue() {
        String password = passwordGenerator.generatePassword(8);
        assertTrue(password.matches(".*[a-z].*"));
    }

    @Test
    public void generatePassword_ContainsUpperCase_ReturnsTrue() {
        String password = passwordGenerator.generatePassword(8);
        assertTrue(password.matches(".*[A-Z].*"));
    }

    @Test
    public void generatePassword_ContainsSpecialChar_ReturnsTrue() {
        String password = passwordGenerator.generatePassword(8);
        assertTrue(password.matches(".*[!@#$].*"));
    }

    @Test
    public void generatePassword_ContainsNumber_ReturnsTrue() {
        String password = passwordGenerator.generatePassword(8);
        assertTrue(password.matches(".*[0-9].*"));
    }
}