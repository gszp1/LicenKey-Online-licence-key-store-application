package com.gszp.backend.auth;

import com.gszp.backend.auth.config.JwtService;
import com.gszp.backend.exception.InvalidRegisterRequestException;
import com.gszp.backend.model.User;
import com.gszp.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse authenticate(LoginRequest request) throws UsernameNotFoundException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User %s not found", request.getEmail()));
        }
        var user = userOptional.get();
        return new AuthResponse(jwtService.generateToken(user));
    }

    public AuthResponse register(RegisterRequest request) throws InvalidRegisterRequestException {
        var validationResult = validateRegisterRequest(request);
        if (validationResult.isPresent()) {
            throw new InvalidRegisterRequestException(validationResult.get());
        }
        var firstName = request.getFirstName();
        var lastName = request.getLastName();
        var user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .firstName(((firstName == null) || (firstName.isBlank())) ? null : firstName)
                .lastName(((lastName == null) || (lastName.isBlank())) ? null : lastName)
                .userStatus(UserStatus.ACTIVE)
                .userRole(UserRole.USER)
                .active(true)
                .build();
        user = userRepository.save(user);
        return new AuthResponse(jwtService.generateToken(user));
    }

    private Optional<String> validateRegisterRequest(RegisterRequest request) {

        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return Optional.of("Email is required.");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            return Optional.of("Password is required.");
        }
        if (!request.getPassword().equals(request.getPasswordConfirmation())
        ) {
            return Optional.of("Password and confirmation do not match.");
        }
        if (!request.getEmail().equals(request.getEmailConfirmation())) {
            return Optional.of("Email and confirmation do not match.");
        }
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            return Optional.of("Username is required.");
        }
        if (!CredentialsValidator.validateEmail(request.getEmail())) {
            return Optional.of("Email is invalid.");
        }
        if (!CredentialsValidator.validatePassword(request.getPassword())) {
            return Optional.of("Password is invalid.");
        }
        return Optional.empty();
    }

}
