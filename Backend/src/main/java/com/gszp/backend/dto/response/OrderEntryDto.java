package com.gszp.backend.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntryDto {

    @Builder.Default
    private BigDecimal totalPrice = BigDecimal.ZERO;

    private UUID orderIdentifier;

    private OffsetDateTime placingDate;

    @Builder.Default
    List<OrderEntryDataDto> orderEntries = new ArrayList<>();
}
