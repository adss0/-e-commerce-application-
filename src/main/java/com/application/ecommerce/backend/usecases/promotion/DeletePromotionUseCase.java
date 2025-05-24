package com.application.ecommerce.backend.usecases.promotion;

import com.application.ecommerce.backend.entities.basket.Basket;
import com.application.ecommerce.backend.entities.basket.BasketGateway;
import com.application.ecommerce.backend.entities.basket.BasketId;
import com.application.ecommerce.backend.entities.customer.CustomerId;
import com.application.ecommerce.backend.usecases.promotion.exceptions.NoPromotionCodeException;

public class DeletePromotionUseCase {

    private final BasketGateway basketGateway;

    public DeletePromotionUseCase(BasketGateway basketGateway) {
        this.basketGateway = basketGateway;
    }

    public void execute(Long basketId, Long customerId) throws Exception {
        Basket basket = basketGateway.findById(new BasketId(basketId), new CustomerId(customerId))
                .orElseThrow(() -> new IllegalArgumentException("Basket not found"));

        if (basket.getAppliedPromotionCode() == null ||basket.getAppliedPromotionCode().isEmpty()) {
            throw new NoPromotionCodeException();
        }

        basket.clearPromotion(); // Clear the promotion code
        basketGateway.update(basket);  // Persist the updated basket
    }
}

