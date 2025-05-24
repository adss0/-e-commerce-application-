package com.application.ecommerce.backend.entities.customer;


import com.application.ecommerce.backend.entities.customer.exceptions.InvalidCustomerIdException;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public class CustomerId {
    private final long id;

    public static final CustomerId NONE = new CustomerId();

    private CustomerId() {
        id = 0;
    }

    @JsonCreator  // Tells Jackson to use this constructor for deserialization
    public CustomerId(long id) throws InvalidCustomerIdException {
        if (id <= 0) {
            throw new InvalidCustomerIdException(id);
        }
        this.id = id;
    }

    @JsonValue  // Tells Jackson to use this method for serialization
    public long asLong() {
        return id;
    }

    public String asString() {
        return Long.toString(id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CustomerId that = (CustomerId) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("CustomerId:%d", id);
    }
}