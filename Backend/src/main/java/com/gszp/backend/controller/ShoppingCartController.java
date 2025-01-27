package com.gszp.backend.controller;

import com.gszp.backend.dto.request.AddShoppingCartEntryRequest;
import com.gszp.backend.exception.ExceptionHandler;
import com.gszp.backend.logs.LogGenerator;
import com.gszp.backend.logs.LogTemplate;
import com.gszp.backend.model.ShoppingCart;
import com.gszp.backend.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/shopping-carts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping("")
    public ResponseEntity<?> getShoppingCart(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        try {
            LogGenerator.generateInfoLog(LogTemplate.RECEIVED_REQUEST, "Request for shopping cart entries.");
            return ResponseEntity.ok(shoppingCartService.getShoppingCarts(userDetails.getUsername()));
        } catch(Exception e) {
            return ExceptionHandler.handleException(e);
        }
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteShoppingCart(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            LogGenerator.generateInfoLog(LogTemplate.RECEIVED_REQUEST, "Request for clearing shopping cart.");
            shoppingCartService.clearShoppingCart(userDetails.getUsername());
            return ResponseEntity.ok("Shopping cart has been cleared.");
        } catch(Exception e) {
            return ExceptionHandler.handleException(e);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> addToShoppingCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AddShoppingCartEntryRequest request
    ) {
        try {
            shoppingCartService.addToShoppingCart(userDetails.getUsername(), request);
            return ResponseEntity.ok("Licence was added to shopping cart.");
        } catch (Exception e) {
            return ExceptionHandler.handleException(e);
        }
    }
}
