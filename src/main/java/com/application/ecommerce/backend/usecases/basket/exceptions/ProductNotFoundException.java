package com.application.ecommerce.backend.usecases.basket.exceptions;

import com.application.ecommerce.backend.entities.product.ProductId;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(ProductId productId) {
        super("Product with ID " + productId.asLong() + " not found");
    }
}
