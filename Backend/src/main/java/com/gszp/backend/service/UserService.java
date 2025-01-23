package com.gszp.backend.service;

import com.gszp.backend.auth.util.CredentialsValidator;
import com.gszp.backend.dto.request.UserDataRequest;
import com.gszp.backend.dto.request.UserDataUpdateRequest;
import com.gszp.backend.dto.request.UserPasswordUpdateRequest;
import com.gszp.backend.dto.response.UserDataResponse;
import com.gszp.backend.dto.response.UserDataUpdateResponse;
import com.gszp.backend.exception.InvalidRequestPayloadException;
import com.gszp.backend.exception.OperationNotAllowedConflictException;
import com.gszp.backend.exception.ResourceNotFoundException;
import com.gszp.backend.logs.LogGenerator;
import com.gszp.backend.logs.LogTemplate;
import com.gszp.backend.auth.model.User;
import com.gszp.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDataResponse getUserData(UserDataRequest request) throws ResourceNotFoundException {
        User user = getUser(request.getEmail());
        LogGenerator.generateInfoLog(LogTemplate.REQUEST_SUCCESS, "User data retrieved.");
        return UserDataResponse.fromUser(user);
    }

    public UserDataUpdateResponse updateUserData(
            UserDataUpdateRequest request
    ) throws InvalidRequestPayloadException, OperationNotAllowedConflictException, ResourceNotFoundException {
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            LogGenerator.generateInfoLog(LogTemplate.REQUEST_FAIL, "Invalid request payload.");
            throw new InvalidRequestPayloadException("Username not provided.");
        }
        var existingUser = userRepository.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
            LogGenerator.generateInfoLog(
                    LogTemplate.REQUEST_FAIL,
                    "User with specified username already exists."
            );
            throw new OperationNotAllowedConflictException("Username is already used.");
        }
        User user = getUser(request.getEmail());
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        user.setFirstName(((firstName == null) || (firstName.isBlank())) ? null : firstName);
        user.setLastName(((lastName == null) || (lastName.isBlank())) ? null : lastName);
        user.setUsername(request.getUsername());
        userRepository.save(user);
        LogGenerator.generateInfoLog(LogTemplate.REQUEST_SUCCESS, "User data updated.");
        return new UserDataUpdateResponse("User data updated.");
    }

    public UserDataUpdateResponse updateUserPassword(
            UserPasswordUpdateRequest request
    ) throws InvalidRequestPayloadException, OperationNotAllowedConflictException, ResourceNotFoundException {
        User user = getUser(request.getEmail());
        if (CredentialsValidator.validatePassword(request.getCurrentPassword())) {
            LogGenerator.generateInfoLog(LogTemplate.REQUEST_FAIL, "Provided current password is invalid.");
            throw new InvalidRequestPayloadException("Current password is invalid.");
        }
        if (CredentialsValidator.validatePassword(request.getNewPassword())) {
            LogGenerator.generateInfoLog(LogTemplate.REQUEST_FAIL, "Provided new password is invalid.");
            throw new InvalidRequestPayloadException("New password is invalid.");
        }
        if (!passwordEncoder.matches(request.getCurrentPassword(), request.getNewPassword())) {
            LogGenerator.generateInfoLog(LogTemplate.REQUEST_FAIL, "Current password confirmation is invalid.");
            throw new OperationNotAllowedConflictException("Current password does not match old password.");
        }
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        LogGenerator.generateInfoLog(LogTemplate.REQUEST_SUCCESS, "User password updated.");
        return new UserDataUpdateResponse("User password updated.");
    }

    private User getUser(String email) throws ResourceNotFoundException {
        var userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            LogGenerator.generateInfoLog(LogTemplate.REQUEST_FAIL, "User with given email does not exist.");
            throw new ResourceNotFoundException("User with given email does not exist.");
        }
        return userOptional.get();
    }
}
