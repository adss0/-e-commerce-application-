package com.application.ecommerce.backend.usecases.order.exceptions;

public class EmptyOrderException extends Exception {
    public EmptyOrderException() {
        super("The basket is empty.");
    }
}