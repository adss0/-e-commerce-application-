package com.application.ecommerce.backend.infrastructure.controller.basket;

import com.application.ecommerce.backend.entities.basket.exceptions.InvalidBasketIdException;
import com.application.ecommerce.backend.entities.customer.exceptions.InvalidCustomerIdException;
import com.application.ecommerce.backend.usecases.basket.BasketPublicData;
import com.application.ecommerce.backend.usecases.basket.GetBasketByCustomerIdUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/baskets")
public class GetBasketController {

    private final GetBasketByCustomerIdUseCase getBasketByCustomerIdUseCase;

    public GetBasketController(GetBasketByCustomerIdUseCase useCase) {
        this.getBasketByCustomerIdUseCase = useCase;
    }

    @GetMapping("/{basketId}")
    public ResponseEntity<?> getBasket(
            @RequestParam Long customerId,
            @PathVariable Long basketId) throws InvalidCustomerIdException, InterruptedException, InvalidBasketIdException {
        // Let the exceptions bubble up to the global handler
        BasketPublicData basket = getBasketByCustomerIdUseCase.execute(customerId, basketId);
        return ResponseEntity.ok(basket);
    }

}