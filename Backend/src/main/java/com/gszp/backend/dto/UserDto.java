package com.gszp.backend.dto;

import com.gszp.backend.model.User;
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

    public static UserDto fromUser(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .creationDate(user.get)
                .build();
    }
}
