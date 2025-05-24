package com.application.ecommerce.backend.entities.product.exceptions;

public class InvalidIdException extends Exception {
    public InvalidIdException(long id) {
        super(String.format("%d", id));
    }

    public InvalidIdException(String invalidProductIdOrQuantity) {
    }
}
