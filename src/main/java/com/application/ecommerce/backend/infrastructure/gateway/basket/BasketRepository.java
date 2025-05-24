package com.application.ecommerce.backend.infrastructure.gateway.basket;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<BasketSchema, Long> {
    Optional<BasketSchema> findByBasketIdAndCustomerId(Long basketId, Long customerId);
    void deleteByBasketIdAndCustomerId(Long basketId, Long customerId);
}
