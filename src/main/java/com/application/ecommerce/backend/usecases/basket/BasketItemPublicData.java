package com.application.ecommerce.backend.usecases.basket;

public record BasketItemPublicData(String productId, int quantity, double price) {
}