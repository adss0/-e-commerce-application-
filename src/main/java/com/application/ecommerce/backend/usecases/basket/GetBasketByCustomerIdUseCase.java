package com.application.ecommerce.backend.usecases.basket;

import com.application.ecommerce.backend.entities.basket.Basket;
import com.application.ecommerce.backend.entities.basket.exceptions.InvalidBasketIdException;
import com.application.ecommerce.backend.entities.customer.exceptions.InvalidCustomerIdException;
import com.application.ecommerce.backend.entities.product.ProductGateway;
import com.application.ecommerce.backend.entities.promotion.PromotionGateway;
import com.application.ecommerce.backend.infrastructure.gateway.basket.BasketRepository;
import com.application.ecommerce.backend.infrastructure.gateway.basket.BasketSchema;
import com.application.ecommerce.backend.usecases.basket.exceptions.BasketNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetBasketByCustomerIdUseCase {

    private final BasketRepository basketRepository;
    private final ProductGateway productGateway;
    private final PromotionGateway promotionGateway;

    public GetBasketByCustomerIdUseCase(BasketRepository basketRepository, ProductGateway productGateway, PromotionGateway promotionGateway) {
        this.basketRepository = basketRepository;
        this.productGateway = productGateway;
        this.promotionGateway = promotionGateway;
    }

    public BasketPublicData execute(Long customerId, Long basketId) throws InvalidCustomerIdException, InterruptedException, InvalidBasketIdException {
        if (customerId == null || customerId <= 0) {
            throw new InvalidCustomerIdException(customerId);
        }

        Optional<BasketSchema> basketOptional = basketRepository.findByBasketIdAndCustomerId(
                basketId, customerId);

        // Check if no basket is found
        if (basketOptional.isEmpty()) {
            throw new BasketNotFoundException(basketId);
        }
        // Convert BasketSchema to Basket
        BasketSchema basketSchema = basketOptional.get();
        Basket basket = basketSchema.toBasket(productGateway, promotionGateway); // Convert the schema to a domain object

        // Return the BasketPublicData
        return new BasketPublicData(basket);
    }
}
