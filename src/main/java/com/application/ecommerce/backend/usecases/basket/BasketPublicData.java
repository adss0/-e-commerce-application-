package com.application.ecommerce.backend.usecases.basket;

import com.application.ecommerce.backend.entities.basket.Basket;

import java.util.List;

public class BasketPublicData {
    private final Long basketId;
    private final Long customerId;
    private final List<BasketItemPublicData> items;
    private final String promotionCode;

    public BasketPublicData(Basket basket) {
        this.basketId = basket.getId().asLong();
        this.customerId = basket.getCustomerId().asLong();
        this.items = basket.getItems().stream()
                .map(item -> new BasketItemPublicData(
                        item.getProductId().asString(),
                        item.getQuantity(),
                        item.getPrice().asBigDecimal().doubleValue()  // Convert BigDecimal to double
                ))
                .toList();
        this.promotionCode = basket.getAppliedPromotionCode();
    }

    public String getPromotionCode() {
        return promotionCode;
    }
    public Long getBasketId() {
        return basketId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public List<BasketItemPublicData> getItems() {
        return items;
    }
}
