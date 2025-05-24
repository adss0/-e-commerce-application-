package com.application.ecommerce.backend.entities.basket.exceptions;

public class InvalidQuantityException extends Exception{
    public InvalidQuantityException() {
        super("The quantity provided is invalid.");
    }
}
