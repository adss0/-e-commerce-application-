package com.application.ecommerce.backend.infrastructure.controller.basket;

import com.application.ecommerce.backend.entities.customer.exceptions.InvalidCustomerIdException;
import com.application.ecommerce.backend.usecases.basket.BasketPublicData;
import com.application.ecommerce.backend.usecases.basket.CreateBasketUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/baskets")
public class CreateBasketController {
    private final CreateBasketUseCase createBasketUseCase;

    public CreateBasketController(CreateBasketUseCase createBasketUseCase) {
        this.createBasketUseCase = createBasketUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BasketPublicData createBasket(@RequestParam Long customerId) throws InvalidCustomerIdException {
        return createBasketUseCase.execute(customerId);
    }
}
