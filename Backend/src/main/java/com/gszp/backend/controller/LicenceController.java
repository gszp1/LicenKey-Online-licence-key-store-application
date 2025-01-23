package com.gszp.backend.controller;

import com.gszp.backend.exception.ExceptionHandler;
import com.gszp.backend.service.LicenceService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/licences")
@RequiredArgsConstructor
public class LicenceController {

    private final LicenceService licenceService;

    @GetMapping("/description/{id}")

    public ResponseEntity<?> getLicenceDescription(@PathVariable("id") Long licenceId) {
        try {
            return ResponseEntity.ok(licenceService.getLicenceDescription(licenceId));
        } catch (Exception e) {
            return ExceptionHandler.handleException(e);
        }
    }
}
