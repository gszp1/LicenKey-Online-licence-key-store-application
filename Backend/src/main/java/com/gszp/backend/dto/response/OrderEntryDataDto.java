package com.gszp.backend.dto.response;

import com.gszp.backend.model.Order;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntryDataDto {

    private String licenceName;

    private BigDecimal price;

    private Integer quantity;

    private String imageUrl;

    public static OrderEntryDataDto fromOrder(Order order) {
        return OrderEntryDataDto.builder()
                .price(order.getUnitPrice())
                .licenceName(order.getLicence().getName())
                .quantity(order.getQuantity())
                .imageUrl(order.getLicence().getImageUrl())
                .build();
    }
}
