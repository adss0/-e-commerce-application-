package com.application.ecommerce.backend.entities.product;

import com.application.ecommerce.backend.entities.product.exceptions.InvalidPriceException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductPrice {
    private final BigDecimal price;

    public static final ProductPrice NONE = new ProductPrice();

    private ProductPrice() {
        this.price = BigDecimal.ZERO;
    }
    @JsonCreator  // For deserialization (JSON -> Java)
    public ProductPrice(BigDecimal price) throws InvalidPriceException {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPriceException(String.valueOf(price));
        }
        this.price = price;
    }

    public BigDecimal asBigDecimal() {
        return price;
    }
    @JsonValue  // For serialization (Java -> JSON)
    public String asString() {
        return price.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductPrice that = (ProductPrice) o;
        return price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

    @Override
    public String toString() {
        return String.format("price:%s", price);
    }

}
