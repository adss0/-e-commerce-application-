package com.application.ecommerce.backend.infrastructure.controller.basket;

import com.application.ecommerce.backend.entities.basket.exceptions.InvalidBasketIdException;
import com.application.ecommerce.backend.entities.customer.CustomerId;
import com.application.ecommerce.backend.entities.customer.exceptions.InvalidCustomerIdException;
import com.application.ecommerce.backend.usecases.basket.DeleteBasketUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.application.ecommerce.backend.entities.basket.BasketId;

@RestController
@RequestMapping("/baskets")
public class DeleteBasketController {

    private final DeleteBasketUseCase deleteBasketUseCase;

    public DeleteBasketController(DeleteBasketUseCase deleteBasketUseCase) {
        this.deleteBasketUseCase = deleteBasketUseCase;
    }

    @DeleteMapping("/{customerId}/{basketId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBasket(@PathVariable Long customerId,
                             @PathVariable Long basketId) throws InvalidCustomerIdException, InvalidBasketIdException {
        deleteBasketUseCase.execute(new BasketId(basketId), new CustomerId(customerId));
    }
}
