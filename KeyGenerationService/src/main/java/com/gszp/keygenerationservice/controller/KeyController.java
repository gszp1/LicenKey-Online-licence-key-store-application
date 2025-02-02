package com.gszp.keygenerationservice.controller;

import com.gszp.keygenerationservice.dto.KeyDto;
import com.gszp.keygenerationservice.service.KeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/key")
@RequiredArgsConstructor
public class KeyController
{

    private final KeyService keyService;

    @GetMapping("")
    public ResponseEntity<?> getKey() {
        try {
            return ResponseEntity.ok(new KeyDto(keyService.generateKey()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to generate key due to internal server error");
        }
    }

}
