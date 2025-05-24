package com.application.ecommerce.backend.entities.basket;

import com.application.ecommerce.backend.entities.basket.exceptions.InvalidBasketIdException;

import java.util.Objects;

public class BasketId {
    public static final BasketId NONE = new BasketId();

    private final Long value;

    private BasketId()
    {
        value = null;
    }
    public BasketId(Long value) throws InvalidBasketIdException {
        if(value <= 0){
            throw new InvalidBasketIdException();
        }
        this.value = value;
    }

    public Long asLong() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasketId)) return false;
        BasketId basketId = (BasketId) o;
        return Objects.equals(value, basketId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
