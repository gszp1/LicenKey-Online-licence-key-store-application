package com.gszp.backend.auth.config;

import org.springframework.beans.factory.annotation.Value;

public class JwtService {

    @Value("${secret_key}")
    private String secret;
}
