package com.gszp.orderfunction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gszp.orderfunction.dto.OrderEventDto;
import io.cloudevents.CloudEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class OrderFunction {

    private final ObjectMapper objectMapper;

    @Bean
    public Consumer<CloudEvent> handleOrderEvent() {
        return event -> {
            Optional<OrderEventDto> dtoOp = readEventData(event);
            if (dtoOp.isPresent()) {
                return;
            }
            var eventData = objectMapper.convertValue(event, OrderEventDto.class);

        };
    }

    private Optional<OrderEventDto> readEventData(CloudEvent event) {
        if (event.getData() == null) {
            return Optional.empty();
        }
        try {
            byte[] dataBytes = event.getData().toBytes();
            return Optional.of(objectMapper.readValue(dataBytes, OrderEventDto.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}

