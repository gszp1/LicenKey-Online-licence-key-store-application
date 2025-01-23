package com.gszp.backend.dto.response;

import com.gszp.backend.auth.model.User;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDataResponse {
    private String email;

    private String username;

    private String firstName;

    private String lastName;

    private OffsetDateTime creationDate;

    public static UserDataResponse fromUser(User user) {
        return UserDataResponse.builder()
                .email(user.getEmail())
                .username(user.getTrueUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .creationDate(user.getCreationDate())
                .build();
    }
}
