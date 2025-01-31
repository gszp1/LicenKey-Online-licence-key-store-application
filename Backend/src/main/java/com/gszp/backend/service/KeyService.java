package com.gszp.backend.service;

import com.gszp.backend.dto.response.GetKeysResponse;
import com.gszp.backend.dto.response.KeyDto;
import com.gszp.backend.repository.KeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeyService {

    private final KeyRepository keyRepository;

    public GetKeysResponse getKeys(String userEmail) {
        return new GetKeysResponse(
                keyRepository
                .findAllKeysByUserEmail(userEmail)
                .stream()
                .map(KeyDto::fromKey)
                .toList()
        );
    }
}
