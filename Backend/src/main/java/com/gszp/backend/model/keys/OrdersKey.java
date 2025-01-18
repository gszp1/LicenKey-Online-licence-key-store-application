package com.gszp.backend.model.keys;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class OrdersKey implements Serializable {

    private Long userId;

    private Long licenceId;
}
