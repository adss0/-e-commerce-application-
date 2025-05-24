package com.application.ecommerce.backend.usecases.order.exceptions;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException( Long id) {
        super("Order Not Found for order id :" + id);
    }
}
