package com.gszp.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String email;

    private String username;

    private String firstName;

    private String lastName;

    private String creationDate;
}
