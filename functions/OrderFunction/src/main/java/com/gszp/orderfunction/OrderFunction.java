package com.gszp.orderfunction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gszp.orderfunction.dto.OrderEventDto;
import com.gszp.orderfunction.model.ConfirmedCart;
import com.gszp.orderfunction.model.Order;
import com.gszp.orderfunction.model.OrderKey;
import com.gszp.orderfunction.repository.ConfirmedCartRepository;
import com.gszp.orderfunction.repository.OrderRepository;
import io.cloudevents.CloudEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderFunction {

    private final ObjectMapper objectMapper;

    private final ConfirmedCartRepository confirmedCartRepository;

    private final OrderRepository orderRepository;

    @Bean
    public Consumer<CloudEvent> handleOrderEvent() {
        return event -> {
            Optional<OrderEventDto> dtoOp = readEventData(event);
            if (dtoOp.isEmpty()) {
                log.info("Event data is empty");
                return;
            }
            var eventData = objectMapper.convertValue(event, OrderEventDto.class);
            List<ConfirmedCart> confirmedCartEntries =
                    confirmedCartRepository.getConfirmedCartsByOrOrderIdentifier(eventData.getOrderUUID());
            if (confirmedCartEntries.isEmpty()) {
                log.info("No confirmed cart entries found");
                return;
            }
            final UUID orderUUID = eventData.getOrderUUID();
            var orderEntries = confirmedCartEntries.stream()
                    .map(entry -> mapCartEntryToOrder(entry, orderUUID))
                    .collect(Collectors.toList());
            
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

    private Order mapCartEntryToOrder(ConfirmedCart confirmedCart, UUID orderUUID) {
        return Order.builder()
                .orderId(orderUUID)
                .user(confirmedCart.getUser())
                .licence(confirmedCart.getLicence())
                .key(new OrderKey(confirmedCart.getKey().getUserId(), confirmedCart.getLicence().getLicenceId()))
                .quantity(confirmedCart.getQuantity())
                .unitPrice(confirmedCart.getPrice())
                .build();
    }
}

