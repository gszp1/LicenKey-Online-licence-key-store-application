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
@Table(name = "services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;

    @Column(length = 2083, nullable = false)
    private String apiUrl;

    @OneToMany(mappedBy = "service")
    @Builder.Default
    private List<Licence> licences = new ArrayList<>();
}
