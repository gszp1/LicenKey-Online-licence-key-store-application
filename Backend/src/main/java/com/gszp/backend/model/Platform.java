package com.gszp.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "platforms")
public class Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "platform_id")
    private Long platformId;

    @Column(length = 128, nullable = false, unique = true)
    private String name;

    @Column(length = 2083)
    private String homePage;

    @OneToMany(mappedBy = "platform")
    @Builder.Default
    private List<Licence> licences = new ArrayList<>();
}
