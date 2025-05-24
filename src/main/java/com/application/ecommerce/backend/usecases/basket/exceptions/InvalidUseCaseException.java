package com.application.ecommerce.backend.usecases.basket.exceptions;

public class InvalidUseCaseException extends RuntimeException{
    public InvalidUseCaseException(){
        super("Invalid action. Use add, update.");
    }

}
