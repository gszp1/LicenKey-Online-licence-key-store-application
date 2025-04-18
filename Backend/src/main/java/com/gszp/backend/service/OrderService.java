package com.gszp.backend.service;

import com.gszp.backend.auth.model.User;
import com.gszp.backend.dto.response.GetOrdersResponse;
import com.gszp.backend.dto.response.OrderEntryDataDto;
import com.gszp.backend.dto.response.OrderEntryDto;
import com.gszp.backend.exception.ResourceNotFoundException;
import com.gszp.backend.kafka.KafkaMessageProducer;
import com.gszp.backend.kafka.dto.KafkaOrderMessage;
import com.gszp.backend.logs.LogGenerator;
import com.gszp.backend.logs.LogTemplate;
import com.gszp.backend.model.ConfirmedCart;
import com.gszp.backend.model.Licence;
import com.gszp.backend.model.Order;
import com.gszp.backend.model.ShoppingCart;
import com.gszp.backend.model.keys.ConfirmedCartKey;
import com.gszp.backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ShoppingCartRepository shoppingCartRepository;

    private final UserRepository userRepository;

    private final LicenceRepository licenceRepository;

    private final ConfirmedCartRepository confirmedCartRepository;

    private final KafkaMessageProducer kafkaMessageProducer;

    private final OrderRepository orderRepository;

    public GetOrdersResponse getOrders(String userEmail) {
        List<Order> orders = orderRepository.getAllOrdersByUserEmail(userEmail);
        Map<UUID, List<Order>> groupedOrders = groupOrderEntriesByOrderUUID(orders);
        List<OrderEntryDto> ordersDto = new ArrayList<>();
        for (UUID orderIdentifier : groupedOrders.keySet()) {
            var orderGroup = groupedOrders.get(orderIdentifier);
            var orderDto = OrderEntryDto.builder()
                    .orderIdentifier(orderIdentifier)
                    .placingDate(orderGroup.getFirst().getCreationDate())
                    .build();
            orderDto.setOrderEntries(
                    orderGroup.stream()
                            .map(e -> {
                                orderDto.setTotalPrice(
                                        orderDto.getTotalPrice()
                                                .add(e.getUnitPrice().multiply(BigDecimal.valueOf(e.getQuantity())))
                                );
                                return OrderEntryDataDto.fromOrder(e);
                            }).collect(Collectors.toList())
            );
            ordersDto.add(orderDto);
        }
        return new GetOrdersResponse(ordersDto);
    }

    private Map<UUID, List<Order>> groupOrderEntriesByOrderUUID(List<Order> orders) {
        HashMap<UUID, List<Order>> groupedOrders = new HashMap<>();
        for (Order order : orders) {
            if (!groupedOrders.containsKey(order.getKey().getOrderId())) {
                List<Order> ordersGroup = new ArrayList<>();
                ordersGroup.add(order);
                groupedOrders.put(order.getKey().getOrderId(), ordersGroup);
            } else {
                groupedOrders.get(order.getKey().getOrderId()).add(order);
            }
        }
        return groupedOrders;
    }

    @Transactional
    public UUID createOrder(
            String userEmail
    ) throws ResourceNotFoundException {
        // find user with given email
        var user = getUserByEmail(userEmail);
        // get shopping cart entries
        var shoppingCartEntries = shoppingCartRepository.getShoppingCartsByUserEmail(user.getEmail());
        if (shoppingCartEntries.isEmpty()) {
            LogGenerator.generateInfoLog(LogTemplate.REQUEST_FAIL, "User's shopping cart is empty.");
            throw new ResourceNotFoundException("Shopping cart is empty");
        }
        // generate UUID for order
        final UUID orderUUID = UUID.randomUUID();
        LogGenerator.generateInfoLog(LogTemplate.REQUEST_PROCESSING, "Generated unique UUID for new order.");
        // change cart contents into confirmed cart contents
        List<ConfirmedCart> confirmedCarts = shoppingCartEntries
                .stream()
                .map(entry -> mapToConfirmedCart(entry, orderUUID))
                .toList();
        confirmedCarts = confirmedCartRepository.saveAll(confirmedCarts);
        // update licences
        List<Licence> licences = new ArrayList<>();
        for (ConfirmedCart confirmedCart : confirmedCarts) {
            Licence licence = confirmedCart.getLicence();
            licence.getConfirmedCartEntries().add(confirmedCart);
            licences.add(licence);
        }
        licences = licenceRepository.saveAll(licences);
        // update user
        user.getConfirmedCartEntries().addAll(confirmedCarts);
        user = userRepository.save(user);
        LogGenerator.generateInfoLog(LogTemplate.REQUEST_PROCESSING, "Created confirmed cart entries.");
        // clear cart
        user.getShoppingCartEntries().removeAll(shoppingCartEntries);
        user = userRepository.save(user);
        licences = new ArrayList<>();
        for (var entry : shoppingCartEntries) {
            Licence licence = entry.getLicence();
            licence.getShoppingCartEntries().remove(entry);
            licences.add(licence);
        }
        licences = licenceRepository.saveAll(licences);
        shoppingCartRepository.deleteAll(shoppingCartEntries);
        // send Event with UUID
        LogGenerator.generateInfoLog(LogTemplate.REQUEST_PROCESSING, "Sending event with UUID: " + orderUUID);
        kafkaMessageProducer.sendOrderMessage(new KafkaOrderMessage(orderUUID));

        LogGenerator.generateInfoLog(LogTemplate.REQUEST_SUCCESS, "Successfully handled order.");

        return orderUUID;
    }

    private User getUserByEmail(String userEmail) throws ResourceNotFoundException {
        Optional<User> userOp = userRepository.findByEmail(userEmail);
        if (userOp.isEmpty()) {
            LogGenerator.generateInfoLog(LogTemplate.REQUEST_FAIL, "User with given email does not exist.");
            throw new ResourceNotFoundException("User with given email does not exist");
        }
        return userOp.get();
    }

    private ConfirmedCart mapToConfirmedCart(ShoppingCart shoppingCart, UUID orderUUID) {
        return ConfirmedCart.builder()
                .price(shoppingCart.getLicence().getPrice())
                .quantity(shoppingCart.getQuantity())
                .user(shoppingCart.getUser())
                .licence(shoppingCart.getLicence())
                .user(shoppingCart.getUser())
                .key(new ConfirmedCartKey(
                        shoppingCart.getUser().getUserId(),
                        shoppingCart.getKey().getLicenceId(),
                        orderUUID)
                )
                .build();
    }
}
