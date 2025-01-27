package com.gszp.backend.dto.response;

import com.gszp.backend.model.ShoppingCart;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartDto {

    private Long licenceId;

    private String name;

    private BigDecimal price;

    private Integer quantity;

    public static ShoppingCartDto fromShoppingCart(ShoppingCart shoppingCart) {
        return ShoppingCartDto.builder()
                .licenceId(shoppingCart.getLicence().getLicenceId())
                .name(shoppingCart.getLicence().getName())
                .price(shoppingCart.getLicence().getPrice())
                .quantity(shoppingCart.getQuantity())
                .build();
    }
}
