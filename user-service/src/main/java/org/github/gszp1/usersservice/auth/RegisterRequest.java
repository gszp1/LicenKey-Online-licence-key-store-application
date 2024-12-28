package org.github.gszp1.usersservice.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    private String email;

    private String emailConfirmation;

    private String password;

    private String passwordConfirmation;

    private String username;

    private String firstName;

    private String lastName;
}

