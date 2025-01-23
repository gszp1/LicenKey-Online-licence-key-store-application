package com.gszp.backend.service;

import com.gszp.backend.repository.LicenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LicenceService
{
    private final LicenceRepository licenceRepository;
}
