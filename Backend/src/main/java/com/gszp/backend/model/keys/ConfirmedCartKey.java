package com.gszp.backend.model.keys;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ConfirmedCartKey {

    private Long userId;

    private Long licenceId;
}
