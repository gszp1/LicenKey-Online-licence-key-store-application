package com.gszp.backend.service;

import com.gszp.backend.auth.CredentialsValidator;
import com.gszp.backend.dto.request.UserDataRequest;
import com.gszp.backend.dto.request.UserDataUpdateRequest;
import com.gszp.backend.dto.request.UserPasswordUpdateRequest;
import com.gszp.backend.dto.response.UserDataResponse;
import com.gszp.backend.dto.response.UserDataUpdateResponse;
import com.gszp.backend.exception.InvalidRequestPayloadException;
import com.gszp.backend.exception.OperationNotAllowedConflictException;
import com.gszp.backend.exception.ResourceNotFoundException;
import com.gszp.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserDataResponse getUserData(UserDataRequest request) throws ResourceNotFoundException {
        var userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with given email does not exist");
        }
        return UserDataResponse.fromUser(userOptional.get());
    }

    public UserDataUpdateResponse updateUserData(
            UserDataUpdateRequest request
    ) throws InvalidRequestPayloadException, OperationNotAllowedConflictException, ResourceNotFoundException {
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            throw new InvalidRequestPayloadException("Username not provided.");
        }
        var existingUser = userRepository.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
            throw new OperationNotAllowedConflictException("Username is already used.");
        }
        var userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with given email does not exist.");
        }
        var user = userOptional.get();
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        user.setFirstName(((firstName == null) || (firstName.isBlank())) ? null : firstName);
        user.setLastName(((lastName == null) || (lastName.isBlank())) ? null : lastName);
        user.setUsername(request.getUsername());
        userRepository.save(user);
        return new UserDataUpdateResponse("User data updated.");
    }

    public UserDataUpdateResponse updateUserPassword(
            UserPasswordUpdateRequest request
    ) throws InvalidRequestPayloadException, OperationNotAllowedConflictException, ResourceNotFoundException {
        var userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with given email does not exist.");
        }
        if (CredentialsValidator.validatePassword(request.getCurrentPassword())) {
            throw new InvalidRequestPayloadException("Current password is invalid.");
        }
        if (CredentialsValidator.validatePassword(request.getNewPassword())) {
            throw new InvalidRequestPayloadException("New password is invalid.");
        }
        if (!passwordEncoder.matches(request.getCurrentPassword(), request.getNewPassword())) {
            throw new OperationNotAllowedConflictException("Current password does not match old password.");
        }
        var user = userOptional.get();
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return new UserDataUpdateResponse("User password updated.");
    }
}
