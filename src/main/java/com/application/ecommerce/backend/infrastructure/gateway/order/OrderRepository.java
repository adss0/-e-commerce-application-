package com.application.ecommerce.backend.infrastructure.gateway.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderSchema, Long> {
}