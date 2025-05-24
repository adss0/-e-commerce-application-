package com.application.ecommerce.backend.entities.order.exceptions;

public class InvalidOrderDateException extends Exception{
    public InvalidOrderDateException(String message) {
        super(message);
    }

}
