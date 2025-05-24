package com.application.ecommerce.backend.usecases.product.exceptions;

import com.application.ecommerce.backend.entities.product.ProductName;

public class DuplicateNameException extends Exception {

    public DuplicateNameException(ProductName name) {
        super(String.format("Product name %s not unique", name));
    }
}
