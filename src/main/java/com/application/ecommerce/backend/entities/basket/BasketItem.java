package com.application.ecommerce.backend.entities.basket;

import com.application.ecommerce.backend.entities.basket.exceptions.InvalidBasketItemException;
import com.application.ecommerce.backend.entities.basket.exceptions.InvalidQuantityException;
import com.application.ecommerce.backend.entities.product.ProductId;
import com.application.ecommerce.backend.entities.product.ProductName;
import com.application.ecommerce.backend.entities.product.ProductPrice;

import java.math.BigDecimal;

public class BasketItem {
    private final ProductId productId;
    private int quantity;
    private final ProductPrice price;
    private final ProductName name;


    public BasketItem(ProductId productId, int quantity, ProductPrice price, ProductName name) throws InvalidBasketItemException, InvalidQuantityException {
        if (productId == null) throw new InvalidBasketItemException("ProductId cannot be null");
        if (quantity <= 0) throw new InvalidQuantityException();
        if (price == null) throw new InvalidBasketItemException("Price cannot be null");
        if (name == null) throw new InvalidBasketItemException("Name cannot be null");


        this.productId = productId;
        this.quantity = quantity;
        this.price = price != null ? price : ProductPrice.NONE;  // Fallback to NONE if price is null
        this.name = name;
    }
    public void increaseQuantity(int amount) throws InvalidQuantityException {
        if (amount <= 0) throw new InvalidQuantityException();
        this.quantity += amount;
    }

    public void decreaseQuantity(int amount) throws InvalidQuantityException {
        if (amount <= 0) throw new InvalidQuantityException();
        if (amount > this.quantity) throw new InvalidQuantityException();
        this.quantity -= amount;
    }

    public BigDecimal calculateLineTotal() {
        return price.asBigDecimal().multiply(BigDecimal.valueOf(quantity));
    }
    public ProductId getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public ProductPrice getPrice() {
        return price;
    }
    public ProductName getName() {
        return name;
    }
}
