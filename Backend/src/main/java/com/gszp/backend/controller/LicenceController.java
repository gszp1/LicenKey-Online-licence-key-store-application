package com.gszp.backend.controller;

import com.gszp.backend.exception.ExceptionHandler;
import com.gszp.backend.logs.LogGenerator;
import com.gszp.backend.logs.LogTemplate;
import com.gszp.backend.service.LicenceService;
import lombok.RequiredArgsConstructor;
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
            LogGenerator.generateInfoLog(LogTemplate.RECEIVED_REQUEST, "Request for licence description.");
            return ResponseEntity.ok(licenceService.getLicenceDescription(licenceId));
        } catch (Exception e) {
            return ExceptionHandler.handleException(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getLicences(@RequestParam(value = "keyword", required = false) String keyword) {
        try {
            LogGenerator.generateInfoLog(LogTemplate.RECEIVED_REQUEST, "Request for all licences data.");
            return ResponseEntity.ok(licenceService.getLicences(keyword));
        } catch (Exception e) {
            return ExceptionHandler.handleException(e);
        }
    }

}
