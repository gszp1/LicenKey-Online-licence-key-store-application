package com.gszp.orderfunction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gszp.orderfunction.dto.OrderEventDto;
import com.gszp.orderfunction.model.*;
import com.gszp.orderfunction.repository.*;
import io.cloudevents.CloudEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
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

            // Create Key entries without keyCodes.
            Map<Licence, List<Key>> keysMap = mapOrdersToKeys(orderEntries);
            List<Key> keys = new ArrayList<>();
            for (Licence licence : keysMap.keySet()) {
                keys.addAll(keysMap.get(licence));
            }
            // Update Licences and keys
            keys = keyRepository.saveAll(keys);
            licences = new ArrayList<>();
            for (Licence licence : keysMap.keySet()) {
                List<Key> localKeys = keysMap.get(licence);
                licence.getKeys().addAll(localKeys);
                licences.add(licence);
            }
            // Update Licences with keys
            licences = licenceRepository.saveAll(licences);
            // Update User with key
            user.getKeys().addAll(keys);
            user = userRepository.save(user);

            // TODO: Send event to generate new keys
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
                    .orderId(orderEntry.getOrderId())
                    .build();
            keys.add(key);
        }
        return keys;
    }

}

