package com.gszp.backend.auth;

import lombok.Getter;

@Getter
public class RegisterRequest {

    private String email;

    private String emailConfirmation;

    private String password;

    private String passwordConfirmation;

    private String username;

    private String firstName;

    private String lastName;
}
