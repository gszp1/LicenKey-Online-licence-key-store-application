package com.gszp.backend.model;

import com.gszp.backend.auth.model.User;
import com.gszp.backend.model.keys.ConfirmedCartKey;
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
@Table(name="confirmed_carts")
public class ConfirmedCart {

    @EmbeddedId
    private ConfirmedCartKey key;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name="FK_user_id")
    private User user;

    @ManyToOne
    @MapsId("licenceId")
    @JoinColumn(name="FK_licence_id")
    private Licence licence;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private UUID orderIdentifier;
}
