package com.application.ecommerce.backend.usecases.product.exceptions;

public class NotFoundException extends Exception {

    public NotFoundException(long id) {
        super(String.format("Product %d not found", id));
    }
}
