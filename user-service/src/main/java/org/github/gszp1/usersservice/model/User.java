package org.github.gszp1.usersservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.gszp1.usersservice.auth.Role;
import org.github.gszp1.usersservice.auth.UserStatus;
import org.github.gszp1.usersservice.converter.RoleConverter;
import org.github.gszp1.usersservice.converter.UserStatusConverter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(nullable = false, unique = true)
    private UUID identifier;

    @Column(nullable = false, unique = true, length = 320)
    private String email;

    @Column(nullable = false, length = 256)
    private String passwordHash;

    @Column(length = 100)
    private String username;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Convert(converter = RoleConverter.class)
    private Role userRole;

    @Convert(converter = UserStatusConverter.class)
    private UserStatus userStatus;

    @Column(nullable = false)
    private Boolean active;

    @Column(columnDefinition = "TIMESTAMPTZ", nullable = false)
    private ZonedDateTime creationDate;

    @Column(columnDefinition = "TIMESTAMPTZ")
    private ZonedDateTime deactivationDate;
}
