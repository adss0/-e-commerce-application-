package com.application.ecommerce.backend.entities.basket;

import com.application.ecommerce.backend.entities.basket.exceptions.BasketItemNotFoundException;
import com.application.ecommerce.backend.entities.basket.exceptions.InvalidQuantityException;
import com.application.ecommerce.backend.entities.customer.CustomerId;
import com.application.ecommerce.backend.entities.product.ProductId;
import com.application.ecommerce.backend.entities.product.ProductGateway;
import com.application.ecommerce.backend.entities.promotion.Promotion;
import com.application.ecommerce.backend.entities.promotion.PromotionGateway;
import com.application.ecommerce.backend.usecases.promotion.exceptions.InvalidPromotionCodeException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Basket {
    private final BasketId id;
    private final CustomerId customerId;
    private final List<BasketItem> items;
    private String appliedPromotionCode;

    public Basket(BasketId id, CustomerId customerId, List<BasketItem> items, String appliedPromotionCode) {
        this.id = id != null ? id : BasketId.NONE;
        this.customerId = customerId;
        this.items = new ArrayList<>(items != null ? items : new ArrayList<>());
        this.appliedPromotionCode = appliedPromotionCode;
    }

    public BasketId getId() {
        return id;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public List<BasketItem> getItems() {
        return Collections.unmodifiableList(items);
    }
    public String getAppliedPromotionCode() {
        return appliedPromotionCode;
    }

    public synchronized void addItem(BasketItem newItem) {
        items.stream()
                .filter(item -> item.getProductId().equals(newItem.getProductId()))
                .findFirst()
                .ifPresentOrElse(
                        existingItem -> {
                            try {
                                existingItem.increaseQuantity(newItem.getQuantity());
                            } catch (InvalidQuantityException e) {
                                throw new RuntimeException(e);
                            }
                        },
                        () -> items.add(newItem)
                );
    }

    public synchronized void updateItemQuantity(ProductId productId, int newQuantity)  {
        items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .ifPresentOrElse(
                        item -> {
                            if (newQuantity <= 0) {
                                items.removeIf(i -> i.getProductId().equals(productId));
                            } else {
                                try {
                                    item.decreaseQuantity(item.getQuantity()); // Reset to zero
                                } catch (InvalidQuantityException e) {
                                    throw new RuntimeException(e);
                                }
                                try {
                                    item.increaseQuantity(newQuantity);
                                } catch (InvalidQuantityException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        },
                        () -> {
                            try {
                                throw new BasketItemNotFoundException(productId.asLong());
                            } catch (BasketItemNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
    }

    public BigDecimal calculateTotalWithoutPromotion() {
        return items.stream()
                .map(BasketItem::calculateLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalWithPromotion(BigDecimal discountPercentage) {
        BigDecimal originalTotal = calculateTotalWithoutPromotion();
        if (discountPercentage != null) {
            BigDecimal discountAmount = originalTotal.multiply(discountPercentage)
                    .divide(BigDecimal.valueOf(100));
            return originalTotal.subtract(discountAmount);
        }
        return originalTotal;
    }

    public void applyPromotion(String promotionCode) {
     this.appliedPromotionCode = promotionCode;
    }

    public void clearPromotion() {
        this.appliedPromotionCode = null;
    }
}
