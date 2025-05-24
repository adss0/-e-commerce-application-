package com.application.ecommerce.backend.usecases.order.exceptions;

public class InvalidCreditCardDetailsException extends RuntimeException{
    public InvalidCreditCardDetailsException(String message){
        super(message);
    }
}
