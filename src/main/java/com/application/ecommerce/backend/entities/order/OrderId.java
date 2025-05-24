package com.application.ecommerce.backend.entities.order;

import com.application.ecommerce.backend.entities.order.exceptions.InvalidOrderIdException;
import com.fasterxml.jackson.annotation.JsonValue;

public class OrderId {
    private final long value;

    public OrderId(long value) throws InvalidOrderIdException {
        if(value <= 0)
        {
            throw new InvalidOrderIdException(value);
        }
       this.value = value;
    }

    @JsonValue
    public long asLong() {
        return value;
    }
}