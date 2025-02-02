package com.gszp.keygenerationservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class KeyService {

    @Value("${key.generation.template}")
    private String keyTemplate;

    private final SecureRandom random = new SecureRandom();

    private final String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz0123456789";

    private String generateKey() {
        StringBuilder key = new StringBuilder(keyTemplate);
        for (int i = 0; i < key.length(); ++i) {
            key.setCharAt(i, allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));
        }
        return key.toString();
    }

}
