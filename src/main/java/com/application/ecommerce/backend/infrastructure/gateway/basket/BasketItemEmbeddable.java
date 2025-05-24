package com.application.ecommerce.backend.infrastructure.gateway.basket;

import com.application.ecommerce.backend.entities.basket.BasketItem;
import com.application.ecommerce.backend.entities.basket.exceptions.InvalidBasketItemException;
import com.application.ecommerce.backend.entities.basket.exceptions.InvalidQuantityException;
import com.application.ecommerce.backend.entities.product.ProductId;
import com.application.ecommerce.backend.entities.product.ProductName;
import com.application.ecommerce.backend.entities.product.ProductPrice;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidIdException;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidNameException;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidPriceException;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class BasketItemEmbeddable {
    private Long productId;
    private int quantity;
    private BigDecimal price;
    private String productName;

    // Default constructor
    public BasketItemEmbeddable() {}

    // Constructor to initialize from BasketItem
    public BasketItemEmbeddable(BasketItem item) {
        this.productId = item.getProductId().asLong();
        this.quantity = item.getQuantity();
        this.price = item.getPrice().asBigDecimal();
        this.productName = item.getName().asString();  // Get name from BasketItem
    }

    // Convert to BasketItem
    public BasketItem toBasketItem() throws InvalidIdException, InvalidPriceException, InvalidNameException, InvalidBasketItemException, InvalidQuantityException {
        if (productId == null || productId <= 0) {
            throw new InvalidIdException("Invalid Product ID: " + productId);
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive: " + quantity);
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPriceException("Invalid Price: " + price);
        }
        if (productName == null || productName.trim().isEmpty()) {
            throw new InvalidNameException("Product name cannot be empty");
        }
        return new BasketItem(
                new ProductId(productId),
                quantity,
                new ProductPrice(price),
                new ProductName(productName)  // Create ProductName from productName string
        );
    }

    // Getters and setters for the fields (optional, based on your needs)
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
