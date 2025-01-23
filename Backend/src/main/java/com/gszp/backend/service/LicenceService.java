package com.gszp.backend.service;

import com.gszp.backend.dto.response.LicenceDescriptionResponse;
import com.gszp.backend.exception.ResourceNotFoundException;
import com.gszp.backend.model.Licence;
import com.gszp.backend.repository.LicenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LicenceService {
    private final LicenceRepository licenceRepository;

    public LicenceDescriptionResponse getLicenceDescription(
            Long id
    ) throws ResourceNotFoundException {
       Optional<Licence> licence = licenceRepository.findById(id);
       if (licence.isEmpty()) {
           throw new ResourceNotFoundException("Licence with given id does not exist.");
       }
       return new LicenceDescriptionResponse(licence.get().getDescription());
    }

}
