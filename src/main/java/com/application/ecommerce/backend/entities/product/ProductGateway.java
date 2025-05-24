package com.application.ecommerce.backend.entities.product;

import java.util.List;
import java.util.Optional;

public interface ProductGateway {
    Product create(Product product);
    Optional<Product> findById(Long id);
    boolean checkName(ProductName name);
    List<Product> searchByName(String nameFragment);
    List<Product> findAll();
}
