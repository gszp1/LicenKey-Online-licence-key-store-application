package org.github.gszp1.usersservice.controller;

import org.github.gszp1.usersservice.auth.AuthResponse;
import org.github.gszp1.usersservice.auth.LoginRequest;
import org.github.gszp1.usersservice.auth.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(new AuthResponse("ok"));
    }

    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(new AuthResponse("ok"));
    }

}
