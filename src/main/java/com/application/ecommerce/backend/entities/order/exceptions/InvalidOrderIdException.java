package com.application.ecommerce.backend.entities.order.exceptions;

public class InvalidOrderIdException extends Exception{
    public InvalidOrderIdException(long id) {
    super(String.format("%d", id));
}

    public InvalidOrderIdException(String invalidProductIdOrQuantity) {
    }
}
