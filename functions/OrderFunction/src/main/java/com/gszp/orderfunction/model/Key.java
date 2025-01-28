package com.gszp.orderfunction.model;

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

    @Column(name = "key_code", nullable = true, unique = true)
    @Builder.Default
    private String keyCode = null;

    @Column(nullable = false)
    @Builder.Default
    private Boolean expired = Boolean.FALSE;

    @Column(
            name = "creation_date",
            nullable = false,
            updatable = false,
            insertable = false
    )
    private OffsetDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "FK_licence_id")
    private Licence licence;

    @ManyToOne
    @JoinColumn(name = "FK_user_id")
    private User user;
}
