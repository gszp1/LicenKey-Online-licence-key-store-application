package com.gszp.backend.controller;

import com.gszp.backend.dto.request.UserDataRequest;
import com.gszp.backend.dto.request.UserDataUpdateRequest;
import com.gszp.backend.dto.request.UserPasswordUpdateRequest;
import com.gszp.backend.exception.ExceptionHandler;
import com.gszp.backend.logs.LogGenerator;
import com.gszp.backend.logs.LogTemplate;
import com.gszp.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<?> getUserData(@RequestBody UserDataRequest request) {
        try {
            LogGenerator.generateInfoLog(LogTemplate.RECEIVED_REQUEST, "Request for user data.");
            return ResponseEntity.ok(userService.getUserData(request));
        } catch (Exception e) {
            return ExceptionHandler.handleException(e);
        }
    }

    @PatchMapping("")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<?> updateUserData(@RequestBody UserDataUpdateRequest request) {
        try {
            LogGenerator.generateInfoLog(LogTemplate.RECEIVED_REQUEST, "User data update request.");
            return ResponseEntity.ok(userService.updateUserData(request));
        } catch (Exception e) {
            return ExceptionHandler.handleException(e);
        }
    }

    @PatchMapping("/password")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<?> updateUserPassword(@RequestBody UserPasswordUpdateRequest request) {
        try {
            LogGenerator.generateInfoLog(LogTemplate.RECEIVED_REQUEST, "Password update request.");
            return ResponseEntity.ok(userService.updateUserPassword(request));
        } catch (Exception e) {
            return ExceptionHandler.handleException(e);
        }
    }
}
