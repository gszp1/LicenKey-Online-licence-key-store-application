package com.gszp.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "keys")
public class Key {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "key_id")
    private Long keyId;

    @Column(nullable = false)
    private Boolean expired = Boolean.FALSE;

    @Column(name = "creation_date", nullable = false)
    private OffsetDateTime creationDate = OffsetDateTime.now();

    @ManyToOne
    @JoinColumn(name = "FK_licence_id")
    private Licence licence;

    @ManyToOne
    @JoinColumn(name = "FK_user_id")
    private User user;
}
