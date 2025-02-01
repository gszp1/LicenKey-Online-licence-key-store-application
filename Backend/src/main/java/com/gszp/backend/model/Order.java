package com.gszp.backend.model;

import com.gszp.backend.auth.model.User;
import com.gszp.backend.model.keys.OrderKey;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
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

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(
            name = "creation_date",
            nullable = false,
            updatable = false,
            insertable = false
    )
    private OffsetDateTime creationDate;
}
