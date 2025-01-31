package com.gszp.backend.controller;

import com.gszp.backend.exception.ExceptionHandler;
import com.gszp.backend.service.KeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/keys")
@RequiredArgsConstructor
public class KeyController {

    private final KeyService keyService;

    @GetMapping("")
    public ResponseEntity<?> GetAllKeys(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            return ResponseEntity.ok(keyService.getKeys(userDetails.getUsername()));
        } catch (Exception e) {
            return ExceptionHandler.handleException(e);
        }
    }
}
