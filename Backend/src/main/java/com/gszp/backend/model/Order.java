package com.gszp.backend.model;

import com.gszp.backend.auth.model.User;
import com.gszp.backend.model.keys.OrdersKey;
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
    private OrdersKey key;

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
}
