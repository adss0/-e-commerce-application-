package com.application.ecommerce.backend.usecases.promotion.exceptions;

public class NoPromotionCodeException extends Exception{
    public NoPromotionCodeException(){
        super("No Promotion Code has been applied to the basket");
    }
}
