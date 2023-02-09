package com.yougrocery.yougrocery.authentication.services;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
class PasswordGenerator {

    private static final String CAPITAL_CASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String SPECIAL_CHARACTERS = "!@#$";
    private static final String NUMBERS = "1234567890";
    private static final String COMBINED_CHARS = CAPITAL_CASE_LETTERS + LOWER_CASE_LETTERS + SPECIAL_CHARACTERS + NUMBERS;

    String generatePassword(int length) {
        Random random = new Random();
        char[] password = new char[length];

        password[0] = LOWER_CASE_LETTERS.charAt(random.nextInt(LOWER_CASE_LETTERS.length()));
        password[1] = CAPITAL_CASE_LETTERS.charAt(random.nextInt(CAPITAL_CASE_LETTERS.length()));
        password[2] = SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length()));
        password[3] = NUMBERS.charAt(random.nextInt(NUMBERS.length()));

        for(int i = 4; i< length ; i++) {
            password[i] = COMBINED_CHARS.charAt(random.nextInt(COMBINED_CHARS.length()));
        }
        return new String(password);
    }
}
