package com.gszp.backend.controller;

import com.gszp.backend.dto.request.UserDataRequest;
import com.gszp.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users/")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<?> getUserData(@RequestBody UserDataRequest request) {
        var userData = userService.getUserData(request);
        if (userData.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userData);
    }
}
