package com.application.ecommerce.backend.entities.basket.exceptions;

public class InvalidBasketItemException extends Exception{
    public InvalidBasketItemException(String message) {
        super(message);
    }
}
