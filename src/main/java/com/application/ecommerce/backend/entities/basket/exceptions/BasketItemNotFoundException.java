package com.application.ecommerce.backend.entities.basket.exceptions;

public class BasketItemNotFoundException extends Exception {
    public BasketItemNotFoundException(Long productId) {
        super("Product with ID " + productId + " is not available or does not exist.");
    }
}
