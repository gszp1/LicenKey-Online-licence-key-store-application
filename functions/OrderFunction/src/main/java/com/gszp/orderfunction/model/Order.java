package com.gszp.orderfunction.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

    @EmbeddedId
    private OrderKey key;

    @ManyToOne()
    @MapsId("userId")
    @JoinColumn(name = "FK_user_id")
    private User user;

    @ManyToOne()
    @MapsId("licenceId")
    @JoinColumn(name = "FK_licence_id")
    private Licence licence;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private Integer quantity;
}