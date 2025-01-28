package com.gszp.orderfunction.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Builder.Default
    private Boolean active = Boolean.FALSE;

    @Column(name = "deactivation_date", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime deactivationDate;

    @Column(
            name = "creation_date",
            columnDefinition = "TIMESTAMPTZ",
            nullable = false,
            updatable = false,
            insertable = false
    )
    private OffsetDateTime creationDate;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Key> keys = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<ConfirmedCart> confirmedCartEntries = new ArrayList<>();
}