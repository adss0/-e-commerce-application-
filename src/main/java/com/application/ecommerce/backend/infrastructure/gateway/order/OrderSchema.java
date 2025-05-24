package com.application.ecommerce.backend.infrastructure.gateway.order;

import com.application.ecommerce.backend.entities.customer.CustomerId;
import com.application.ecommerce.backend.entities.customer.exceptions.InvalidCustomerIdException;
import com.application.ecommerce.backend.entities.order.Order;
import com.application.ecommerce.backend.entities.order.OrderDate;
import com.application.ecommerce.backend.entities.order.OrderId;
import com.application.ecommerce.backend.entities.order.exceptions.InvalidOrderDateException;
import com.application.ecommerce.backend.entities.order.exceptions.InvalidOrderIdException;
import com.application.ecommerce.backend.entities.product.Product;
import com.application.ecommerce.backend.entities.product.ProductGateway;
import com.application.ecommerce.backend.entities.product.ProductId;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidIdException;
import com.application.ecommerce.backend.entities.promotion.PromotionGateway;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;
@Entity
@Table(name = "orders")
public class OrderSchema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @ElementCollection
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "product_id")
    private List<Long> productIds;

    @ElementCollection
    @CollectionTable(name = "order_product_quantities", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Long, Integer> productQuantities;

    @Column(nullable = true)
    private String promotionCode;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal originalTotal;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal discountedTotal;

    @Version
    private Long version;

    // Constructors, getters, and setters
    public OrderSchema() {}

    public OrderSchema(Order order) {
        this.customerId = order.getCustomerId().asLong();
        this.orderDate = order.getOrderDate().getValue();
        this.productIds = order.getProducts().stream()
                .map(product -> product.getId().asLong())
                .collect(Collectors.toList());
        this.productQuantities = order.getProductQuantities().entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().asLong(),
                        Map.Entry::getValue
                ));
        this.promotionCode = order.getPromotionCode();
        this.originalTotal = order.getOriginalTotal();
        this.discountedTotal = order.getDiscountedTotal();
    }

    public Order toOrder(ProductGateway productGateway, PromotionGateway promotionGateway)
            throws InvalidCustomerIdException, InvalidOrderIdException, InvalidOrderDateException {
        Map<ProductId, Integer> productQuantities = this.productQuantities.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> {
                            try {
                                return new ProductId(entry.getKey());
                            } catch (InvalidIdException e) {
                                throw new RuntimeException(e);
                            }
                        },  // Convert Long to ProductId
                        Map.Entry::getValue
                ));
        List<Product> products = this.productIds.stream()
                .map(productId -> productGateway.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found for ID: " + productId)))
                .collect(Collectors.toList());

        Order order = new Order(
                new OrderId(this.id),
                new CustomerId(this.customerId),
                new OrderDate(this.orderDate),
                products,
                productQuantities,
                this.promotionCode,
                this.originalTotal,
                this.discountedTotal
        );
        order.setVersion(this.version);  // Set the version from schema
        return order;
    }

    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public List<Long> getProductIds() { return productIds; }
    public Map<Long, Integer> getProductQuantities() { return productQuantities; }
    public String getPromotionCode() { return promotionCode; }
    public BigDecimal getOriginalTotal() { return originalTotal; }
    public BigDecimal getDiscountedTotal() { return discountedTotal; }

    public void setVersion(Long version) { this.version = version; }
    public Long getVersion() { return version; }
}