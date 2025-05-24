package com.application.ecommerce.backend.usecases.promotion.exceptions;

public class InvalidPromotionCodeException  extends Exception {
    public InvalidPromotionCodeException (String code) {
        super(String.format("Promotion code '%s' is invalid or not found", code));
    }
}