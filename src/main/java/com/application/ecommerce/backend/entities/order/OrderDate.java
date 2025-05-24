package com.application.ecommerce.backend.entities.order;

import com.application.ecommerce.backend.entities.order.exceptions.InvalidOrderDateException;

import java.time.LocalDateTime;

public class OrderDate {

    private LocalDateTime value;

    public OrderDate(LocalDateTime value) throws InvalidOrderDateException {
        if(value == null || value.isAfter(LocalDateTime.now())){
            throw new InvalidOrderDateException("Invalid Order date");
        }
        this.value = value;
    }

    public LocalDateTime getValue() {
        return value;
    }

    public void setValue(LocalDateTime value) {
        this.value = value;
    }
}