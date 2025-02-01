package com.gszp.backend.controller;

import com.gszp.backend.exception.ExceptionHandler;
import com.gszp.backend.logs.LogGenerator;
import com.gszp.backend.logs.LogTemplate;
import com.gszp.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            LogGenerator.generateInfoLog(LogTemplate.RECEIVED_REQUEST, "Received request for placing and order");
            return ResponseEntity.ok(orderService.createOrder(userDetails.getUsername()));
        } catch (Exception e) {
            return ExceptionHandler.handleException(e);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getOrders(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            LogGenerator.generateInfoLog(LogTemplate.RECEIVED_REQUEST, "Received request for orders");
            return ResponseEntity.ok(orderService.getOrders(userDetails.getUsername()));
        } catch (Exception e) {
            return ExceptionHandler.handleException(e);
        }
    }
}
