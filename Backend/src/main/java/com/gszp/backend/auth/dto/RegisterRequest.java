package com.gszp.backend.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {

    private String email;

    private String emailConfirmation;

    private String password;

    private String passwordConfirmation;

    private String username;

    private String firstName;

    private String lastName;
}
