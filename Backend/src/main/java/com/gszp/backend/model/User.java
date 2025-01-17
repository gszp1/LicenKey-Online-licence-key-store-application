package com.gszp.backend.model;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private Boolean active = Boolean.FALSE;

    @Column(name = "deactivation_date", columnDefinition = "TIMESTAMPTZ")
    private String deactivationDate;
}
