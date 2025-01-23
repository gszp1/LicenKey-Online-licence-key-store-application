package com.gszp.backend.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LicenceDto {

    private Long licenceId;

    private String name;

    private String category;

    private String publisher;

    private String developer;

    private BigDecimal price;

    private String platform;

    private String type;

    private Boolean availableForSale;
}
