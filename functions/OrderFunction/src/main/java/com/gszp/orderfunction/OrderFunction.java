package com.gszp.orderfunction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gszp.orderfunction.dto.OrderEventDto;
import com.gszp.orderfunction.model.*;
import com.gszp.orderfunction.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.*;
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

    private final KeyRepository keyRepository;

    @Bean
    @Transactional
    public Consumer<Message<String>> handleOrderEvent() {
        return event -> {
            log.info("Received event: {}", event.getPayload());
            Optional<OrderEventDto> dtoOp = readEventData(event);
            if (dtoOp.isEmpty()) {
                log.info("Event data is empty");
                return;
            }
            // Retrieve event data from CloudEvent
            var eventData = dtoOp.get();
            log.info("Read event data: {}", eventData.getOrderUUID());
            List<ConfirmedCart> confirmedCartEntries =
                    confirmedCartRepository.getConfirmedCartsByOrOrderIdentifier(eventData.getOrderUUID());
            if (confirmedCartEntries.isEmpty()) {
                log.info("No confirmed cart entries found");
                return;
            }
            log.info("Retrieved cart entries in quantity: {}", confirmedCartEntries.size());

            // Map cart entries to order entries
            final UUID orderUUID = eventData.getOrderUUID();
            var orderEntries = confirmedCartEntries.stream()
                    .map(entry -> mapCartEntryToOrder(entry, orderUUID))
                    .collect(Collectors.toList());
            orderEntries = orderRepository.saveAll(orderEntries);
            log.info("Mapped cart entries to order entries.");

            // Update user record
            var user = userRepository.getByUserId(orderEntries.getFirst().getUser().getUserId());
            user.getOrders().addAll(orderEntries);
            log.info("Orders added to licences.");

            // Update licences records
            List<Long> licencesIds = orderEntries
                    .stream().map(e -> e.getLicence().getLicenceId()).toList();
            List<Licence> licencesList = licenceRepository.getLicencesByLicenceId(licencesIds);
            Map<Long, Licence> licences = licencesList.stream()
                    .collect(Collectors.toMap(Licence::getLicenceId, e -> e));
            for (Order order : orderEntries) {
                Licence licence = order.getLicence();
                if (!licences.containsKey(licence.getLicenceId())) {
                    licence.getOrders().add(order);
                    licences.put(licence.getLicenceId(), licence);
                } else {
                    licences.get(licence.getLicenceId()).getOrders().add(order);
                }
            }
            log.info("Licences records updated.");
            List<Licence> allLicenses = licences.values().stream().toList();
            allLicenses = licenceRepository.saveAll(allLicenses);

            // Clear Confirmed Shopping Cart
            confirmedCartRepository.deleteAll(confirmedCartEntries);
            log.info("Cleared confirmed shopping cart.");

            // Create Key entries without keyCodes.
            Map<Licence, List<Key>> keysMap = mapOrdersToKeys(orderEntries);
            List<Key> keys = new ArrayList<>();
            for (Licence licence : keysMap.keySet()) {
                keys.addAll(keysMap.get(licence));
            }
            keys = keyRepository.saveAll(keys);
            log.info("Created key entries.");

            // Update User with key
            user = userRepository.save(user);

            // TODO: Send event to generate new keys
        };
    }

    private Optional<OrderEventDto> readEventData(Message<String> event) {
        String payload = event.getPayload();
        if (payload.isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(objectMapper.readValue(payload, OrderEventDto.class));
        } catch (Exception e) {
            log.error("Error reading event data", e);
            return Optional.empty();
        }
    }

    private Order mapCartEntryToOrder(ConfirmedCart confirmedCart, UUID orderUUID) {
        return Order.builder()
                .user(confirmedCart.getUser())
                .licence(confirmedCart.getLicence())
                .key(new OrderKey(
                                confirmedCart.getKey().getUserId(),
                                confirmedCart.getLicence().getLicenceId(),
                                orderUUID
                        )
                )
                .quantity(confirmedCart.getQuantity())
                .unitPrice(confirmedCart.getPrice())
                .build();
    }

    private Map<Licence, List<Key>> mapOrdersToKeys(List<Order> orderEntries) {
        HashMap<Licence, List<Key>> keysMap = new HashMap<>();
        for (Order order : orderEntries) {
            keysMap.put(order.getLicence(), mapOrderEntryToKeys(order));
        }
        return keysMap;
    }

    private List<Key> mapOrderEntryToKeys(Order orderEntry) {
        List<Key> keys = new ArrayList<>();
        for (int i = 0; i < orderEntry.getQuantity(); ++i) {
            Key key = Key.builder()
                    .user(orderEntry.getUser())
                    .licence(orderEntry.getLicence())
                    .orderId(orderEntry.getKey().getOrderId())
                    .build();
            keys.add(key);
        }
        return keys;
    }

}

