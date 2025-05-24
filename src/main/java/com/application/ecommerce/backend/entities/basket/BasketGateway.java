package com.application.ecommerce.backend.entities.basket;

import com.application.ecommerce.backend.usecases.basket.exceptions.BasketNotFoundException;
import com.application.ecommerce.backend.entities.customer.CustomerId;
import com.application.ecommerce.backend.entities.customer.exceptions.InvalidCustomerIdException;

import java.util.List;
import java.util.Optional;

public interface BasketGateway {
    Basket create(Basket basket);

    Optional<Basket> findById(BasketId basketId, CustomerId customerId) throws InvalidCustomerIdException;

    List<Basket> findAll();

    Basket update(Basket basket);

    void delete(BasketId basketId, CustomerId customerId) throws BasketNotFoundException;
}
