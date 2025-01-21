package com.gszp.backend.auth;

import com.gszp.backend.exception.InvalidRegisterRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            return ResponseEntity.ok(authService.register(request));
        } catch (InvalidRegisterRequestException irre) {
            irre.printStackTrace();
            return ResponseEntity.badRequest().body(irre);
        } catch (DataIntegrityViolationException dive) {
            dive.printStackTrace();
            return ResponseEntity.badRequest().body("User already exists");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.authenticate(request));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).body("Bad credentials");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }
}
