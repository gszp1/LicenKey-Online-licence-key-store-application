package com.gszp.orderfunction.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ConfirmedCartKey {

    private Long userId;

    private Long licenceId;

    private UUID orderIdentifier;
}
