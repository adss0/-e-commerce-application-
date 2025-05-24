package com.application.ecommerce.backend.usecases.basket;

import com.application.ecommerce.backend.entities.basket.Basket;
import com.application.ecommerce.backend.entities.promotion.Promotion;
import com.application.ecommerce.backend.entities.promotion.PromotionGateway;

import java.math.BigDecimal;
import java.util.Optional;

public class CalculateBasketTotalUseCase {
    private final PromotionGateway promotionGateway;

    public CalculateBasketTotalUseCase(PromotionGateway promotionGateway) {
        this.promotionGateway = promotionGateway;
    }

    public BigDecimal execute(Basket basket) {
        BigDecimal originalTotal = basket.calculateTotalWithoutPromotion();

        if (basket.getAppliedPromotionCode() != null) {
            Optional<Promotion> promotion = promotionGateway.findByCode(basket.getAppliedPromotionCode());
            if (promotion.isPresent()) {
                return basket.calculateTotalWithPromotion(promotion.get().getDiscountPercent());
            }
        }
        return originalTotal;
    }
}