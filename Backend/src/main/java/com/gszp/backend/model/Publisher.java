package com.gszp.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "publishers")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    private Long platformId;

    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "home_page", length = 2083)
    private String homePage;
}
