package com.application.ecommerce.backend.usecases.basket.exceptions;

public class BasketNotFoundException extends RuntimeException {
    public BasketNotFoundException(Long basketId) {
        super("Basket not found with ID: " + basketId);
    }
}