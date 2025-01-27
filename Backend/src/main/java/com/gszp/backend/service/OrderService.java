package com.gszp.backend.service;

import com.gszp.backend.auth.model.User;
import com.gszp.backend.exception.ResourceNotFoundException;
import com.gszp.backend.logs.LogGenerator;
import com.gszp.backend.logs.LogTemplate;
import com.gszp.backend.repository.LicenceRepository;
import com.gszp.backend.repository.ShoppingCartRepository;
import com.gszp.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ShoppingCartService shoppingCartService;

    private final ShoppingCartRepository shoppingCartRepository;

    private final UserRepository userRepository;

    private final LicenceRepository licenceRepository;

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
    }

    private User getUserByEmail(String userEmail) throws ResourceNotFoundException {
        Optional<User> userOp = userRepository.findByEmail(userEmail);
        if (userOp.isEmpty()) {
            LogGenerator.generateInfoLog(LogTemplate.REQUEST_FAIL, "User with given email does not exist.");
            throw new ResourceNotFoundException("User with given email does not exist");
        }
        return userOp.get();
    }
}
