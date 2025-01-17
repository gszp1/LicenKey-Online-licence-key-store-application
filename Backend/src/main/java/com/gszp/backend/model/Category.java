package com.gszp.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private List<Licence> licences = new ArrayList<>();
}
