package com.gszp.backend.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Builder
public class AuthResponse {
    private String contents;
}
