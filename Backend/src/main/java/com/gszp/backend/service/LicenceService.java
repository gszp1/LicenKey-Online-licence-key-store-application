package com.gszp.backend.service;

import com.gszp.backend.dto.response.LicenceDescriptionResponse;
import com.gszp.backend.dto.response.LicenceDto;
import com.gszp.backend.dto.response.LicencesResponse;
import com.gszp.backend.exception.ResourceNotFoundException;
import com.gszp.backend.logs.LogGenerator;
import com.gszp.backend.logs.LogTemplate;
import com.gszp.backend.model.Licence;
import com.gszp.backend.repository.LicenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LicenceService {
    private final LicenceRepository licenceRepository;

    public LicenceDescriptionResponse getLicenceDescription(
            Long id
    ) throws ResourceNotFoundException {
        Optional<Licence> licence = licenceRepository.findById(id);
        if (licence.isEmpty()) {
            LogGenerator.generateInfoLog(LogTemplate.REQUEST_FAIL, "Licence with given ID does not exist.");
            throw new ResourceNotFoundException("Licence with given id does not exist.");
        }
        LogGenerator.generateInfoLog(LogTemplate.REQUEST_SUCCESS, "Successfully retrieved licence description.");
        return new LicenceDescriptionResponse(licence.get().getDescription());
    }

    public LicencesResponse getLicences(String keyword) {
        LogGenerator.generateInfoLog(LogTemplate.REQUEST_SUCCESS, "Successfully retrieved licences data.");
        return new LicencesResponse(licenceRepository.getLicencesByKeyword(keyword)
                .stream()
                .map(LicenceDto::fromLicence)
                .collect(Collectors.toList())
        );
    }
}
