package com.gszp.backend.auth;

import com.gszp.backend.exception.InvalidRequestPayloadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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
            log.info("Received register request.");
            return ResponseEntity.ok(authService.register(request));
        } catch (InvalidRequestPayloadException irre) {
            log.error("Register request failed due to invalid credentials.");
            return ResponseEntity.badRequest().body(irre);
        } catch (DataIntegrityViolationException dive) {
            log.error("Register request failed due to existing user with given credentials.");
            return ResponseEntity.badRequest().body("User already exists");
        } catch (Exception e) {
            log.error("Register request failed due to internal service error.");
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            log.info("Received login request.");
            return ResponseEntity.ok(authService.authenticate(request));
        } catch (AuthenticationException ex) {
            log.info("Login request failed due to authentication failure.");
            return ResponseEntity.status(401).body("Bad credentials.");
        } catch (Exception e) {
            log.info("Login request failed due to internal service error.");
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }
}
