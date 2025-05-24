package com.application.ecommerce.backend.infrastructure.controller.basket;

import com.application.ecommerce.backend.entities.basket.exceptions.InvalidBasketIdException;
import com.application.ecommerce.backend.entities.basket.exceptions.InvalidBasketItemException;
import com.application.ecommerce.backend.entities.basket.exceptions.InvalidQuantityException;
import com.application.ecommerce.backend.entities.customer.exceptions.InvalidCustomerIdException;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidIdException;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidPriceException;
import com.application.ecommerce.backend.usecases.basket.BasketPublicData;
import com.application.ecommerce.backend.usecases.basket.exceptions.ProductNotFoundException;
import com.application.ecommerce.backend.usecases.basket.UpdateBasketUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/baskets")
public class UpdateBasketController {

    private final UpdateBasketUseCase updateBasketUseCase;

    public UpdateBasketController(UpdateBasketUseCase updateBasketUseCase) {
        this.updateBasketUseCase = updateBasketUseCase;
    }

    @PutMapping("/{customerId}/{basketId}")
    public BasketPublicData updateBasket(@PathVariable Long customerId,
                                         @PathVariable Long basketId, // Added basketId here
                                         @RequestParam Long productId,
                                         @RequestParam int quantity,
                                         @RequestParam(defaultValue = "add") String action)
            throws InvalidCustomerIdException, InvalidIdException, InvalidPriceException, ProductNotFoundException, InvalidQuantityException, InvalidBasketItemException, InvalidBasketIdException {

        return updateBasketUseCase.execute(customerId, basketId, productId, quantity, action);
    }
}