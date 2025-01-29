package com.gszp.orderfunction.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEventDto {

    private UUID orderUUID;
}
