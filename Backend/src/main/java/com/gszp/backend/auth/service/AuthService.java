package com.gszp.backend.auth.service;

import com.gszp.backend.auth.dto.AuthResponse;
import com.gszp.backend.auth.dto.LoginRequest;
import com.gszp.backend.auth.dto.RegisterRequest;
import com.gszp.backend.auth.model.UserRole;
import com.gszp.backend.auth.model.UserStatus;
import com.gszp.backend.auth.util.CredentialsValidator;
import com.gszp.backend.exception.InvalidRequestPayloadException;
import com.gszp.backend.exception.ResourceAlreadyExistsException;
import com.gszp.backend.exception.ResourceNotFoundException;
import com.gszp.backend.logs.LogGenerator;
import com.gszp.backend.logs.LogTemplate;
import com.gszp.backend.auth.model.User;
import com.gszp.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    public AuthResponse authenticate(
            LoginRequest request
    ) throws UsernameNotFoundException, ResourceNotFoundException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            LogGenerator.generateInfoLog(LogTemplate.REQUEST_FAIL, "User with given email does not exist.");
            throw new ResourceNotFoundException("User with given email does not exist.");
        }
        var user = userOptional.get();
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("Role", user.getUserRole().name());
        var authToken = jwtService.generateToken(user, extraClaims);
        LogGenerator.generateInfoLog(LogTemplate.REQUEST_SUCCESS, "User authenticated.");
        return new AuthResponse(authToken);
    }

    public AuthResponse register(
            RegisterRequest request
    ) throws InvalidRequestPayloadException, ResourceAlreadyExistsException {
        var validationResult = validateRegisterRequest(request);
        if (validationResult.isPresent()) {
            LogGenerator.generateInfoLog(LogTemplate.REQUEST_FAIL, "Invalid request payload.");
            throw new InvalidRequestPayloadException(validationResult.get());
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
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException dive) {
            dive.printStackTrace();
            LogGenerator.generateInfoLog(LogTemplate.REQUEST_FAIL, "Credentials already used.");
            throw new ResourceAlreadyExistsException("User with given credentials already exists.");
        }
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("Role", user.getUserRole().name());
        var authToken = jwtService.generateToken(user, extraClaims);
        LogGenerator.generateInfoLog(LogTemplate.REQUEST_SUCCESS, "User registered.");
        return new AuthResponse(authToken);
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
