package com.gszp.backend.model;

import jakarta.persistence.*;
import lombok.*;

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
}
