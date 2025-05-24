package com.application.ecommerce.backend.usecases.order;

import com.application.ecommerce.backend.entities.order.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderPublicData {
    private final Long orderId;
    private final Long customerId;
    private final String orderDate;
    private final List<OrderItemPublicData> items;
    private final String promotionCode;
    private final double originalTotal;
    private final double discountedTotal;
    private final double discountAmount;

    public OrderPublicData(Order order) {
        this.orderId = order.getId().asLong();
        this.customerId = order.getCustomerId().asLong();
        this.orderDate = order.getOrderDate().getValue().toString();
        this.items = order.getProducts().stream()
                .map(product -> new OrderItemPublicData(
                        product.getId().asString(),
                        order.getQuantityForProduct(product.getId()),
                        product.getPrice().asBigDecimal().doubleValue()
                ))
                .collect(Collectors.toList());
        this.promotionCode = order.getPromotionCode();
        this.originalTotal = order.getOriginalTotal().doubleValue();
        this.discountedTotal = order.getDiscountedTotal().doubleValue();
        this.discountAmount = this.originalTotal - this.discountedTotal;
    }

    // Getters
    public Long getOrderId() { return orderId; }
    public Long getCustomerId() { return customerId; }
    public String getOrderDate() { return orderDate; }
    public List<OrderItemPublicData> getItems() { return items; }
    public String getPromotionCode() { return promotionCode; }
    public double getOriginalTotal() { return originalTotal; }
    public double getDiscountedTotal() { return discountedTotal; }
    public double getDiscountAmount() { return discountAmount; }
}