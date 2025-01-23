package com.gszp.backend.auth.controller;

import com.gszp.backend.auth.dto.LoginRequest;
import com.gszp.backend.auth.dto.RegisterRequest;
import com.gszp.backend.auth.service.AuthService;
import com.gszp.backend.exception.ExceptionHandler;
import com.gszp.backend.logs.LogGenerator;
import com.gszp.backend.logs.LogTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            LogGenerator.generateInfoLog(LogTemplate.RECEIVED_REQUEST, "register.");
            return ResponseEntity.ok(authService.register(request));
        } catch (Exception e) {
            return ExceptionHandler.handleException(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LogGenerator.generateInfoLog(LogTemplate.RECEIVED_REQUEST, "login.");
            return ResponseEntity.ok(authService.authenticate(request));
        } catch (Exception e) {
            return ExceptionHandler.handleException(e);
        }
    }
}
