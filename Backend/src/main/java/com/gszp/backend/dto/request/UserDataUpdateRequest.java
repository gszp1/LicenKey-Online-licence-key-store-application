package com.gszp.backend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDataUpdateRequest {

    private String email;

    private String username;

    private String firstName;

    private String lastName;
}
