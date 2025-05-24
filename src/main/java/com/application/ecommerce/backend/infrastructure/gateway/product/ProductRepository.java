package com.application.ecommerce.backend.infrastructure.gateway.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductSchema, Long> {
    Optional<ProductSchema> findByName(String name);
}
