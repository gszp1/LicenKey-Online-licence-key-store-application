package com.gszp.backend.service;

import com.gszp.backend.auth.model.User;
import com.gszp.backend.dto.request.ShoppingCartEntryRequest;
import com.gszp.backend.dto.response.GetShoppingCartsResponse;
import com.gszp.backend.dto.response.ShoppingCartDto;
import com.gszp.backend.exception.ResourceNotFoundException;
import com.gszp.backend.logs.LogGenerator;
import com.gszp.backend.logs.LogTemplate;
import com.gszp.backend.model.Licence;
import com.gszp.backend.model.ShoppingCart;
import com.gszp.backend.model.keys.ShoppingCartKey;
import com.gszp.backend.repository.LicenceRepository;
import com.gszp.backend.repository.ShoppingCartRepository;
import com.gszp.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    private final UserRepository userRepository;

    private final LicenceRepository licenceRepository;

    public GetShoppingCartsResponse getShoppingCarts(
            String userEmail
    ) throws ResourceNotFoundException {
        var shoppingCartEntries = shoppingCartRepository.getShoppingCartsByUserEmail(userEmail)
                .stream()
                .map(ShoppingCartDto::fromShoppingCart)
                .collect(Collectors.toList());
        LogGenerator.generateInfoLog(LogTemplate.REQUEST_SUCCESS, "Retrieved shopping cart entries.");
        return new GetShoppingCartsResponse(shoppingCartEntries);
    }

    public void clearShoppingCart(String userEmail) {
        var shoppingCartEntries = shoppingCartRepository.getShoppingCartsByUserEmail(userEmail);
        shoppingCartRepository.deleteAll(shoppingCartEntries);
        LogGenerator.generateInfoLog(LogTemplate.REQUEST_SUCCESS, "Cleared shopping cart.");
    }

    public void addToShoppingCart(
            String userEmail, ShoppingCartEntryRequest request
    ) throws ResourceNotFoundException {
        var user = getUserByEmail(userEmail);
        var licence = getLicenceById(request.getLicenceId());
        var shoppingCartOp = shoppingCartRepository
                .getShoppingCartByUserIdAndLicenceId(user.getUserId(), licence.getLicenceId());
        if (shoppingCartOp.isEmpty()) { // There is no entry for this licence in table
            ShoppingCart shoppingCart = ShoppingCart.builder()
                    .key(new ShoppingCartKey(user.getUserId(), licence.getLicenceId()))
                    .licence(licence)
                    .user(user)
                    .quantity(1)
                    .build();
            shoppingCart = shoppingCartRepository.save(shoppingCart);
            user.getShoppingCartEntries().add(shoppingCart);
            userRepository.save(user);
            licence.getShoppingCartEntries().add(shoppingCart);
            licenceRepository.save(licence);
            LogGenerator.generateInfoLog(LogTemplate.REQUEST_SUCCESS,
                    "New Shopping cart entry was added. (New record)"
            );
            return;
        } // There is already entry for this licence
        var shoppingCart = shoppingCartOp.get();
        shoppingCart.setQuantity(shoppingCart.getQuantity() + 1);
        shoppingCartRepository.save(shoppingCart);
        LogGenerator.generateInfoLog(LogTemplate.REQUEST_SUCCESS,
                "New Shopping cart entry was added. (Updated quantity)"
        );
    }

    public void increaseQuantity(
            String userEmail,
            ShoppingCartEntryRequest request
    ) throws ResourceNotFoundException {
        var shoppingCartOp = getShoppingCartEntry(userEmail, request.getLicenceId());
        if (shoppingCartOp.isEmpty()) {
            LogGenerator.generateInfoLog(LogTemplate.REQUEST_FAIL, "Given shopping cart entry does not exist.");
            throw new ResourceNotFoundException("Given shopping cart entry does not exist.");
        }
        var shoppingCart = shoppingCartOp.get();
        shoppingCart.setQuantity(shoppingCart.getQuantity() + 1);
        shoppingCartRepository.save(shoppingCart);
        LogGenerator.generateInfoLog(LogTemplate.REQUEST_SUCCESS,
                "Increased quantity of shopping cart entry."
        );
    }

    public void decreaseQuantity(
            String userEmail,
            ShoppingCartEntryRequest request
    ) throws ResourceNotFoundException {
        var user = getUserByEmail(userEmail);
        var licence = getLicenceById(request.getLicenceId());
        var shoppingCartOp = shoppingCartRepository.
                getShoppingCartByUserIdAndLicenceId(user.getUserId(), licence.getLicenceId());
        if (shoppingCartOp.isEmpty()) {
            LogGenerator.generateInfoLog(LogTemplate.REQUEST_FAIL, "Given shopping cart entry does not exist.");
            throw new ResourceNotFoundException("Given shopping cart entry does not exist.");
        }
        var shoppingCart = shoppingCartOp.get();

        if (shoppingCart.getQuantity() == 1) { // Remove from shopping cart if quantity drops to 0
            user.getShoppingCartEntries().remove(shoppingCart);
            userRepository.save(user);
            licence.getShoppingCartEntries().remove(shoppingCart);
            licenceRepository.save(licence);
            shoppingCartRepository.delete(shoppingCart);
            return;
        }
        shoppingCart.setQuantity(shoppingCart.getQuantity() - 1);
        shoppingCartRepository.save(shoppingCart);
        LogGenerator.generateInfoLog(LogTemplate.REQUEST_SUCCESS,
                "Increased quantity of shopping cart entry."
        );
    }

    private User getUserByEmail(String userEmail) throws ResourceNotFoundException {
        Optional<User> userOp = userRepository.findByEmail(userEmail);
        if (userOp.isEmpty()) {
            LogGenerator.generateInfoLog(LogTemplate.REQUEST_FAIL, "User with given email does not exist.");
            throw new ResourceNotFoundException("User with given email does not exist");
        }
        return userOp.get();
    }

    private Licence getLicenceById(Long licenceId) throws ResourceNotFoundException {
        Optional<Licence> licenceOp = licenceRepository.findById(licenceId);
        if (licenceOp.isEmpty()) {
            LogGenerator.generateInfoLog(LogTemplate.REQUEST_FAIL, "Licence with given id does not exist.");
            throw new ResourceNotFoundException("Licence with given id does not exist");
        }
        return licenceOp.get();
    }

    private Optional<ShoppingCart> getShoppingCartEntry(
        String userEmail,
        Long licenceId
    ) throws ResourceNotFoundException {
        var user = getUserByEmail(userEmail);
        var licence = getLicenceById(licenceId);
        return shoppingCartRepository.getShoppingCartByUserIdAndLicenceId(user.getUserId(), licence.getLicenceId());
    }
}
