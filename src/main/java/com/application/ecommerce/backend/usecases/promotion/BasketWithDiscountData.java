package com.application.ecommerce.backend.usecases.promotion;

public class BasketWithDiscountData {

    private Long basketId;
    private Long customerId;
    private double originalTotal;
    private String promotionCode;
    private int discountPercent;
    private double finalTotal;

    public BasketWithDiscountData(Long basketId, Long customerId, double originalTotal, String promotionCode, int discountPercent, double finalTotal) {
        this.basketId = basketId;
        this.customerId = customerId;
        this.originalTotal = originalTotal;
        this.promotionCode = promotionCode;
        this.discountPercent = discountPercent;
        this.finalTotal = finalTotal;
    }

    // Getters and setters
    public Long getBasketId() {
        return basketId;
    }

    public void setBasketId(Long basketId) {
        this.basketId = basketId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public double getOriginalTotal() {
        return originalTotal;
    }

    public void setOriginalTotal(double originalTotal) {
        this.originalTotal = originalTotal;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public double getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(double finalTotal) {
        this.finalTotal = finalTotal;
    }
}
