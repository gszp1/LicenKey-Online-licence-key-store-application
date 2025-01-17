package com.gszp.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "FK_platform_id")
    private Platform platform;

    @ManyToOne
    @JoinColumn(name = "FK_licence_type_id")
    private LicenceType licenceType;

    @ManyToOne
    @JoinColumn(name = "FK_category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "FK_publisher_id")
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "FK_service_id")
    private Service service;

    @OneToMany(mappedBy = "licence")
    @Builder.Default
    private List<Key> keys = new ArrayList<>();
}
