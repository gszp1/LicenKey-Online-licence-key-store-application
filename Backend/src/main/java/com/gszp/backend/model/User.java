package com.gszp.backend.model;

import com.gszp.backend.util.UserRole;
import com.gszp.backend.util.UserRoleConverter;
import com.gszp.backend.util.UserStatus;
import com.gszp.backend.util.UserStatusConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(length = 320, nullable = false, unique = true)
    private String email;

    @Column(length = 256, nullable = false)
    private String passwordHash;

    @Column(length = 100, nullable = false, unique = true)
    private String username;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Convert(converter = UserStatusConverter.class)
    @Column(name = "user_status")
    private UserStatus userStatus;

    @Convert(converter = UserRoleConverter.class)
    @Column(name = "user_role")
    private UserRole userRole;

    @Column(nullable = false)
    private Boolean active = Boolean.FALSE;

    @Column(name = "deactivation_date", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime deactivationDate;
}
