package com.application.ecommerce.backend.entities.product.exceptions;

public class InvalidPriceException extends Exception {
    public InvalidPriceException(int price) {
        super(String.format("invalid price %d", price));
    }

    public InvalidPriceException(String invalidProductIdOrQuantity) {
    }
}