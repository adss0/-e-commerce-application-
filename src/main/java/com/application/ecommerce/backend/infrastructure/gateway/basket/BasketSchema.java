package com.application.ecommerce.backend.infrastructure.gateway.basket;

import com.application.ecommerce.backend.entities.basket.Basket;
import com.application.ecommerce.backend.entities.basket.BasketId;
import com.application.ecommerce.backend.entities.basket.BasketItem;
import com.application.ecommerce.backend.entities.basket.exceptions.InvalidBasketIdException;
import com.application.ecommerce.backend.entities.basket.exceptions.InvalidBasketItemException;
import com.application.ecommerce.backend.entities.basket.exceptions.InvalidQuantityException;
import com.application.ecommerce.backend.entities.customer.CustomerId;
import com.application.ecommerce.backend.entities.customer.exceptions.InvalidCustomerIdException;
import com.application.ecommerce.backend.entities.product.ProductGateway;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidIdException;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidNameException;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidPriceException;
import com.application.ecommerce.backend.entities.promotion.PromotionGateway;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "baskets")
public class BasketSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long basketId;  // ID in the database

    @Column(name = "customer_id")
    private Long customerId;  // Customer ID

    @ElementCollection
    @CollectionTable(name = "basket_items", joinColumns = @JoinColumn(name = "basket_id"))
    private List<BasketItemEmbeddable> items;

    @Column(name = "applied_promotion_code")
    private String appliedPromotionCode;    // Default constructor
    public BasketSchema() {}

    // Constructor to create BasketSchema from Basket
    public BasketSchema(Basket basket) {
        this.basketId = basket.getId().asLong();
        this.customerId = basket.getCustomerId().asLong();
        this.items = basket.getItems().stream()
                .map(BasketItemEmbeddable::new)
                .collect(Collectors.toList());

        this.appliedPromotionCode = basket.getAppliedPromotionCode();  // Store promotion code as a String
    }

    // Convert BasketSchema back to Basket (using PromotionGateway to resolve the code to a full Promotion object)
    public Basket toBasket(ProductGateway productGateway, PromotionGateway promotionGateway) throws InvalidCustomerIdException, InvalidBasketIdException {
        List<BasketItem> domainItems = this.items.stream()
                .map(item -> {
                    try {
                        return item.toBasketItem();
                    } catch (InvalidIdException | InvalidPriceException | InvalidBasketItemException |
                             InvalidNameException | InvalidQuantityException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        return new Basket(new BasketId(this.basketId), new CustomerId(this.customerId), domainItems, appliedPromotionCode);
    }

    public String getAppliedPromotionCode() {
        return appliedPromotionCode;
    }

    public void setAppliedPromotionCode(String appliedPromotionCode) {
        this.appliedPromotionCode = appliedPromotionCode;
    }

    // Getter and Setter for other fields
    public Long getBasketId() {
        return basketId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public List<BasketItemEmbeddable> getItems() {
        return items;
    }

    public void setItems(List<BasketItemEmbeddable> items) {
        this.items = items;
    }

    // Checks if the basket belongs to the given customer
    public boolean belongsToCustomer(Long customerId) {
        return this.customerId.equals(customerId);
    }
}


