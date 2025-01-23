package com.gszp.backend.dto.response;

import com.gszp.backend.model.Licence;
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

    public static LicenceDto fromLicence(Licence licence) {
        return LicenceDto.builder()
                .name(licence.getName())
                .availableForSale(licence.getAvailableForSale())
                .licenceId(licence.getLicenceId())
                .price(licence.getPrice())
                .developer(licence.getDeveloper())
                .category(licence.getCategory() == null ? "Not-Stated" : licence.getCategory().getName())
                .platform(licence.getPlatform() == null ? "Not-Stated" : licence.getPlatform().getName())
                .publisher(licence.getPublisher() == null ? "Not-Stated" : licence.getPublisher().getName())
                .type(licence.getLicenceType() == null ? "Not_Stated" : licence.getLicenceType().getName())
                .build();
    }
}
