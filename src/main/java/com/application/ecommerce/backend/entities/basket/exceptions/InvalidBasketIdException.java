package com.application.ecommerce.backend.entities.basket.exceptions;

public class InvalidBasketIdException extends Exception {
    public InvalidBasketIdException() {
        super("The basket ID is invalid.");
    }
}
