package com.gszp.orderfunction.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class OrderKey implements Serializable {

    private Long userId;

    private Long licenceId;
}
