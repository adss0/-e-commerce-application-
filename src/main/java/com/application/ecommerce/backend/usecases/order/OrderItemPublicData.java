package com.application.ecommerce.backend.usecases.order;

public record OrderItemPublicData(String productId, int quantity, double price) {
}