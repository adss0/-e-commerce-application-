package com.application.ecommerce.backend.infrastructure.gateway.order;

import com.application.ecommerce.backend.entities.customer.exceptions.InvalidCustomerIdException;
import com.application.ecommerce.backend.entities.order.Order;
import com.application.ecommerce.backend.entities.order.OrderGateway;
import com.application.ecommerce.backend.entities.order.OrderId;
import com.application.ecommerce.backend.entities.order.exceptions.InvalidOrderDateException;
import com.application.ecommerce.backend.entities.order.exceptions.InvalidOrderIdException;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.application.ecommerce.backend.entities.product.ProductGateway;
import com.application.ecommerce.backend.entities.promotion.PromotionGateway;
import org.springframework.beans.factory.annotation.Autowired;
@Repository
public class OrderDatabaseGateway implements OrderGateway {

    private final OrderRepository orderRepository;
    private final ProductGateway productGateway;
    private final PromotionGateway promotionGateway;

    @Autowired
    public OrderDatabaseGateway(OrderRepository orderRepository, ProductGateway productGateway, PromotionGateway promotionGateway) {
        this.orderRepository = orderRepository;
        this.productGateway = productGateway;
        this.promotionGateway = promotionGateway;
    }

    @Transactional
    @Override
    public void save(Order order) {
        try {
            OrderSchema schema = new OrderSchema(order);
            OrderSchema savedSchema = orderRepository.save(schema);
            // Update the order with the generated ID
            order.setId(new OrderId(savedSchema.getId()));
        } catch (OptimisticLockException ex) {
            throw new RuntimeException("Failed to save order due to concurrent modification", ex);
        } catch (InvalidOrderIdException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Optional<Order> findById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(schema -> {
                    try {
                        return schema.toOrder(productGateway, promotionGateway);
                    } catch (InvalidCustomerIdException | InvalidOrderIdException | InvalidOrderDateException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
    @Override
    public List<Order> findAll() {
        return orderRepository.findAll().stream()
                .map(schema -> {
                    try {
                        return schema.toOrder(productGateway, promotionGateway);
                    } catch (InvalidCustomerIdException | InvalidOrderIdException | InvalidOrderDateException e) {
                        throw new RuntimeException("Error converting order schema", e);
                    }
                })
                .collect(Collectors.toList());
    }
}
