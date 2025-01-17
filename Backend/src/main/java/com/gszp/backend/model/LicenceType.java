package com.gszp.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "licence_types")
public class LicenceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeId;

    @Column(name = "duration_days")
    private Integer durationDays;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "licenceType")
    @Builder.Default
    private List<Licence> licences = new ArrayList<>();
}
