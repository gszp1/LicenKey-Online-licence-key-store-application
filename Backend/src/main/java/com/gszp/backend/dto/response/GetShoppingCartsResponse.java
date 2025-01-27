package com.gszp.backend.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetShoppingCartsResponse {

    private List<ShoppingCartDto> shoppingCartEntries;
}
