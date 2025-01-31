package com.gszp.orderfunction.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ConfirmedCartKey implements Serializable {

    private Long userId;

    private Long licenceId;

    private UUID orderIdentifier;
}
