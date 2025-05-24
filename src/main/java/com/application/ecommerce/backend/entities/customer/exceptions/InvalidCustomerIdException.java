package com.application.ecommerce.backend.entities.customer.exceptions;

public class InvalidCustomerIdException extends Exception {

    public InvalidCustomerIdException(long id) {
        super(String.format("Invalid customer ID: %d", id));
    }

    public InvalidCustomerIdException(String message) {
        super(message);
    }
}
