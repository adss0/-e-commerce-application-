package com.application.ecommerce.backend.entities.order;

import java.util.List;
import java.util.Optional;

public interface OrderGateway {
    void save(Order order);
    Optional<Order> findById(Long orderId);
    List<Order> findAll();  // Add this new method
}
