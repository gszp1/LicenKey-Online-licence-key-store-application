package com.gszp.backend.service;

import com.gszp.backend.dto.request.UserDataRequest;
import com.gszp.backend.dto.response.UserDataResponse;
import com.gszp.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<UserDataResponse> getUserData(UserDataRequest request) {
        var userOptional = userRepository.findByEmail(request.getEmail());
        return userOptional.map(UserDataResponse::fromUser);
    }
}
