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

    public String generateKey() {
        String template = keyTemplate == null ? "XXXX-XXXX-XXXX" : keyTemplate;
        StringBuilder key = new StringBuilder(template);
        for (int i = 0; i < key.length(); ++i) {
            if (key.charAt(i) == 'X') {
                key.setCharAt(i, allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));
            }
        }
        return key.toString();
    }

}
