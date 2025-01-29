package com.gszp.orderfunction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gszp.orderfunction.dto.OrderEventDto;
import com.gszp.orderfunction.model.*;
import com.gszp.orderfunction.repository.ConfirmedCartRepository;
import com.gszp.orderfunction.repository.LicenceRepository;
import com.gszp.orderfunction.repository.OrderRepository;
import com.gszp.orderfunction.repository.UserRepository;
import io.cloudevents.CloudEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    private final LicenceRepository licenceRepository;

    private final UserRepository userRepository;

    @Bean
    @Transactional
    public Consumer<CloudEvent> handleOrderEvent() {
        return event -> {
            Optional<OrderEventDto> dtoOp = readEventData(event);
            if (dtoOp.isEmpty()) {
                log.info("Event data is empty");
                return;
            }
            // Retrieve event data from CloudEvent
            var eventData = objectMapper.convertValue(event, OrderEventDto.class);
            List<ConfirmedCart> confirmedCartEntries =
                    confirmedCartRepository.getConfirmedCartsByOrOrderIdentifier(eventData.getOrderUUID());
            if (confirmedCartEntries.isEmpty()) {
                log.info("No confirmed cart entries found");
                return;
            }
            // Map cart entries to order entries
            final UUID orderUUID = eventData.getOrderUUID();
            var orderEntries = confirmedCartEntries.stream()
                    .map(entry -> mapCartEntryToOrder(entry, orderUUID))
                    .collect(Collectors.toList());
            orderEntries = orderRepository.saveAll(orderEntries);
            // Update user record
            var user = orderEntries.getFirst().getUser();
            user.getOrders().addAll(orderEntries);
            // Update licences records
            List<Licence> licences = new ArrayList<>();
            for (Order order : orderEntries) {
                Licence licence = order.getLicence();
                licence.getOrders().add(order);
                licences.add(licence);
            }
            licences = licenceRepository.saveAll(licences);
            // Clear Confirmed Shopping Cart
            clearConfirmedCart(confirmedCartEntries, user);

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

    private void clearConfirmedCart(
            List<ConfirmedCart> confirmedCartEntries, User user
    ) {
        user.getConfirmedCartEntries().removeAll(confirmedCartEntries);
        userRepository.save(user);
        List<Licence> licences = new ArrayList<>();
        confirmedCartEntries.forEach(confirmedCart -> {
            Licence licence = confirmedCart.getLicence();
            licence.getConfirmedCartEntries().remove(confirmedCart);
            licences.add(licence);
        });
        licenceRepository.saveAll(licences);
        confirmedCartRepository.deleteAll(confirmedCartEntries);
    }
}

