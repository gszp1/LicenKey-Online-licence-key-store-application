package com.gszp.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "licences")
public class Licence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "licence_id")
    private Long licenceId;

    @Column(length = 128, unique = true, nullable = false)
    private String name;

    @Column(length = 128, nullable = false)
    private String developer;

    private String description;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "available_for_sale", nullable = false)
    private Boolean AvailableForSale = true;
}
