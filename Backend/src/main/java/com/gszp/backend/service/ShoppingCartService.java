package com.gszp.backend.service;

import com.gszp.backend.auth.model.User;
import com.gszp.backend.dto.response.GetShoppingCartsResponse;
import com.gszp.backend.dto.response.ShoppingCartDto;
import com.gszp.backend.exception.ResourceNotFoundException;
import com.gszp.backend.logs.LogGenerator;
import com.gszp.backend.logs.LogTemplate;
import com.gszp.backend.model.ShoppingCart;
import com.gszp.backend.repository.ShoppingCartRepository;
import com.gszp.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    private final UserRepository userRepository;

    public GetShoppingCartsResponse getShoppingCarts(
            String userEmail
    ) throws ResourceNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (userOptional.isEmpty()) {
            LogGenerator.generateInfoLog(LogTemplate.REQUEST_FAIL, "User with given email does not exist.");
            throw new ResourceNotFoundException("User with given email does not exist.");
        }
        var shoppingCartEntries = shoppingCartRepository.getShoppingCartsByUserEmail(userEmail)
                .stream()
                .map(ShoppingCartDto::fromShoppingCart)
                .collect(Collectors.toList());
        LogGenerator.generateInfoLog(LogTemplate.REQUEST_SUCCESS, "Retrieved shopping cart entries.");
        return new GetShoppingCartsResponse(shoppingCartEntries);
    }
}
