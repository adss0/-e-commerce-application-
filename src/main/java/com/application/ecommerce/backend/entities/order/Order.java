package com.application.ecommerce.backend.entities.order;

import com.application.ecommerce.backend.entities.customer.CustomerId;
import com.application.ecommerce.backend.entities.product.Product;
import com.application.ecommerce.backend.entities.product.ProductId;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Order {
    private Long version;
    private OrderId id;
    private final CustomerId customerId;
    private final OrderDate orderDate;
    private final List<Product> products;
    private final Map<ProductId, Integer> productQuantities;
    private final String promotionCode;
    private final BigDecimal originalTotal;
    private final BigDecimal discountedTotal;

    public Order(OrderId id, CustomerId customerId, OrderDate orderDate,
                 List<Product> products, Map<ProductId, Integer> productQuantities, String promotionCode,
                 BigDecimal originalTotal, BigDecimal discountedTotal) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.products = products;
        this.productQuantities = productQuantities;
        this.promotionCode = promotionCode;
        this.originalTotal = originalTotal;
        this.discountedTotal = discountedTotal;
        this.version = null;
    }

    public OrderId getId() { return id; }
    public CustomerId getCustomerId() { return customerId; }
    public OrderDate getOrderDate() { return orderDate; }
    public List<Product> getProducts() { return products; }
    public Map<ProductId, Integer> getProductQuantities() { return productQuantities; }
    public String getPromotionCode() { return promotionCode; }
    public BigDecimal getOriginalTotal() { return originalTotal; }
    public BigDecimal getDiscountedTotal() { return discountedTotal; }
    public Long getVersion() { return version; }

    public void setId(OrderId id) { this.id = id; }
    public void setVersion(Long version) { this.version = version; }

    public int getQuantityForProduct(ProductId productId) {
        return productQuantities.getOrDefault(productId, 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id) && customerId.equals(order.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId);
    }
}