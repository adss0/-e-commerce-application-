package com.application.ecommerce.backend.usecases.promotion;

import com.application.ecommerce.backend.entities.basket.Basket;
import com.application.ecommerce.backend.entities.basket.BasketGateway;
import com.application.ecommerce.backend.entities.basket.BasketId;
import com.application.ecommerce.backend.entities.customer.CustomerId;
import com.application.ecommerce.backend.entities.promotion.Promotion;
import com.application.ecommerce.backend.entities.promotion.PromotionGateway;
import com.application.ecommerce.backend.usecases.promotion.exceptions.InvalidPromotionCodeException;
import com.application.ecommerce.backend.usecases.promotion.exceptions.NoPromotionCodeException;
import com.application.ecommerce.backend.usecases.promotion.exceptions.PromotionAlreadyAppliedException;

import java.math.BigDecimal;

public class ApplyPromotionUseCase {

    private final BasketGateway basketGateway;
    private final PromotionGateway promotionGateway;  // Inject the PromotionGateway

    public ApplyPromotionUseCase(BasketGateway basketGateway, PromotionGateway promotionGateway) {
        this.basketGateway = basketGateway;
        this.promotionGateway = promotionGateway;
    }

    public BasketWithDiscountData execute(Long basketId, Long customerId, String code) throws Exception {
        Basket basket = basketGateway.findById(new BasketId(basketId), new CustomerId(customerId))
                .orElseThrow(() -> new NoPromotionCodeException());

        if (basket.getAppliedPromotionCode() != null) {
            throw new PromotionAlreadyAppliedException();
        }

        Promotion promotion = promotionGateway.findByCode(code)
                .orElseThrow(() -> new InvalidPromotionCodeException(code));

        basket.applyPromotion(promotion.getCode());

        basketGateway.update(basket);

        BigDecimal total = basket.calculateTotalWithoutPromotion();
        BigDecimal discount = total.multiply(promotion.getDiscountPercent()).divide(BigDecimal.valueOf(100));  // Discount calculation
        BigDecimal discountedTotal = total.subtract(discount);

        return new BasketWithDiscountData(
                basketId, customerId, total.doubleValue(), code, promotion.getDiscountPercent().intValue(), discountedTotal.doubleValue());
    }
}
