package com.application.ecommerce.backend.infrastructure.gateway.basket;

import com.application.ecommerce.backend.entities.basket.Basket;
import com.application.ecommerce.backend.entities.basket.BasketGateway;
import com.application.ecommerce.backend.entities.basket.BasketId;
import com.application.ecommerce.backend.entities.basket.exceptions.InvalidBasketIdException;
import com.application.ecommerce.backend.usecases.basket.exceptions.BasketNotFoundException;
import com.application.ecommerce.backend.entities.customer.CustomerId;
import com.application.ecommerce.backend.entities.customer.exceptions.InvalidCustomerIdException;
import com.application.ecommerce.backend.entities.product.ProductGateway;
import com.application.ecommerce.backend.entities.promotion.PromotionGateway;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BasketDatabaseGateway implements BasketGateway {

    private final BasketRepository repository;
    private final ProductGateway productGateway;
    private  final PromotionGateway promotionGateway;

    public BasketDatabaseGateway(BasketRepository repository, ProductGateway productGateway, PromotionGateway promotionGateway) {
        this.repository = repository;
        this.productGateway = productGateway;
        this.promotionGateway = promotionGateway;
    }

    @Override
    public Basket create(Basket basket) {
        BasketSchema savedSchema = repository.save(new BasketSchema(basket));
        try {
            return savedSchema.toBasket(productGateway, promotionGateway);
        } catch (InvalidCustomerIdException | InvalidBasketIdException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Optional<Basket> findById(BasketId basketId, CustomerId customerId) throws InvalidCustomerIdException {
        Optional<BasketSchema> basketSchema = repository.findByBasketIdAndCustomerId(basketId.asLong(), customerId.asLong());

        if (basketSchema.isEmpty()) {
            throw new InvalidCustomerIdException("No basket found for this customer with the provided basketId");
        }

        try {
            return Optional.of(basketSchema.get().toBasket(productGateway, promotionGateway));
        } catch (InvalidBasketIdException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Basket> findAll() {
        return repository.findAll().stream()
                .map(schema -> {
                    try {
                        return schema.toBasket(productGateway, promotionGateway);
                    } catch (InvalidCustomerIdException | InvalidBasketIdException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public Basket update(Basket basket) {
        BasketSchema schema = new BasketSchema(basket);
        BasketSchema updatedSchema = repository.save(schema);
        try {
            return updatedSchema.toBasket(productGateway, promotionGateway);
        } catch (InvalidCustomerIdException | InvalidBasketIdException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    @Transactional
    public void delete(BasketId basketId, CustomerId customerId)  {
        try {
            repository.deleteByBasketIdAndCustomerId(basketId.asLong(), customerId.asLong());
        } catch (Exception e) {
            throw new BasketNotFoundException(basketId.asLong());
        }
    }
}

