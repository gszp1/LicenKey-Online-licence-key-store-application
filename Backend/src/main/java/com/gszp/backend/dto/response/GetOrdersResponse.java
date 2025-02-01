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
public class GetOrdersResponse {

    private BigDecimal totalPrice;

    private UUID orderIdentifier;

    private OffsetDateTime placingDate;

    @Builder.Default
    List<OrderDataDto> orderEntries = new ArrayList<>();
}
