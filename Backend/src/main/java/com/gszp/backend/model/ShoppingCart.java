package com.gszp.backend.model;

import com.gszp.backend.model.keys.ShoppingCartKey;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shopping_carts")
public class ShoppingCart {

    @EmbeddedId
    private ShoppingCartKey key;

    @ManyToOne()
    @MapsId("userId")
    @JoinColumn(name = "FK_user_id")
    private User user;

    @ManyToOne()
    @MapsId("licenceId")
    @JoinColumn(name = "FK_licence_id")
    private Licence licence;

    @Column(nullable = false)
    private Integer quantity;
}
