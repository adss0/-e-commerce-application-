package com.application.ecommerce.backend.usecases.basket;

import com.application.ecommerce.backend.entities.basket.Basket;
import com.application.ecommerce.backend.entities.basket.BasketGateway;
import com.application.ecommerce.backend.entities.customer.CustomerId;
import com.application.ecommerce.backend.entities.customer.exceptions.InvalidCustomerIdException;
import com.application.ecommerce.backend.entities.product.ProductGateway;
import com.application.ecommerce.backend.entities.promotion.PromotionGateway;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CreateBasketUseCase {

    private final BasketGateway basketGateway;

    public CreateBasketUseCase(BasketGateway gateway) {
        this.basketGateway = gateway;
    }

    public BasketPublicData execute(Long customerId) throws InvalidCustomerIdException {
        if (customerId == null) {
            throw new InvalidCustomerIdException("Customer ID cannot be null");
        }

        Basket basket = new Basket(null, new CustomerId(customerId), Collections.emptyList(), null);
        Basket createdBasket = basketGateway.create(basket);
        return new BasketPublicData(createdBasket);
    }
}
