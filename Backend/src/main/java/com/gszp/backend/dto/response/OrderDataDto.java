package com.gszp.backend.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDataDto {

    private String licenceName;

    private BigDecimal price;

    private Integer quantity;
}
