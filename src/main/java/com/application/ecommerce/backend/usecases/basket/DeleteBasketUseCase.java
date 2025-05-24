package com.application.ecommerce.backend.usecases.basket;

import com.application.ecommerce.backend.entities.basket.Basket;
import com.application.ecommerce.backend.entities.basket.BasketGateway;
import com.application.ecommerce.backend.entities.basket.BasketId;
import com.application.ecommerce.backend.entities.customer.CustomerId;
import com.application.ecommerce.backend.entities.customer.exceptions.InvalidCustomerIdException;
import com.application.ecommerce.backend.usecases.basket.exceptions.BasketNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteBasketUseCase {
    private final BasketGateway basketGateway;

    public DeleteBasketUseCase(BasketGateway basketGateway) {
        this.basketGateway = basketGateway;
    }
    @Transactional
    public void execute(BasketId basketId, CustomerId customerId)
            throws BasketNotFoundException, InvalidCustomerIdException {
        Optional<Basket> basket = basketGateway.findById(basketId, customerId);

        if (basket.isEmpty()) {
            throw new BasketNotFoundException(basketId.asLong());
        }
        basketGateway.delete(basketId, customerId);
    }
}