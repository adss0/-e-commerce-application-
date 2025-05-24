package com.application.ecommerce.backend.entities.product;

import com.application.ecommerce.backend.entities.product.exceptions.InvalidNameException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public class ProductName {
    private final String name;
    public static final int MAX_LENGTH = 20;
    static final ProductName NONE = new ProductName();

    private ProductName() {
        name = "";
    }
    @JsonCreator
    public ProductName(String name) throws InvalidNameException {

        if (Objects.isNull(name) || name.isBlank()) {
            throw new InvalidNameException("Not null or blank");
        }
        if (name.length() > MAX_LENGTH) {
            throw new InvalidNameException(String.format("Max Length %d", MAX_LENGTH));
        }
        this.name = name;
    }
    @JsonValue
    public String asString() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductName that = (ProductName) o;
        return name.equalsIgnoreCase(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name.toUpperCase());
    }

    @Override
    public String toString() {
        return String.format("name:%s", name);
    }
}
