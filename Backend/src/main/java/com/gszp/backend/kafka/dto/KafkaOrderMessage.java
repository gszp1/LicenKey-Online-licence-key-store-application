package com.gszp.backend.kafka.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaOrderMessage {

    private UUID orderUUID;
}
