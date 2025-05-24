package com.application.ecommerce.backend.usecases.promotion.exceptions;

public class PromotionAlreadyAppliedException extends Exception{
    public PromotionAlreadyAppliedException(){
        super ("A promotion code has already been applied");
    }
}
