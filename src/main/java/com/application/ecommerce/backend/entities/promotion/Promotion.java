package com.application.ecommerce.backend.entities.promotion;


import java.math.BigDecimal;

public class Promotion {
    private final String code;
    private final BigDecimal discountPercent;

    public Promotion(String code, BigDecimal discountPercent) {
        this.code = code;
        this.discountPercent = discountPercent;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }
}
