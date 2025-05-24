package com.application.ecommerce.backend.infrastructure.gateway.product;

import com.application.ecommerce.backend.entities.product.Product;
import com.application.ecommerce.backend.entities.product.ProductGateway;
import com.application.ecommerce.backend.entities.product.ProductName;

import java.util.List;
import java.util.Optional;

public class ProductDatabaseGateway implements ProductGateway {
    private final ProductRepository productRepository;

    public ProductDatabaseGateway(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        return ProductSchema.toProduct(this.productRepository.save(new ProductSchema(product)));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id).map(ProductSchema::toProduct);
    }

    @Override
    public boolean checkName(ProductName name) {
        return productRepository.findByName(name.asString()).isPresent();
    }

    @Override
    public List<Product> searchByName(String nameFragment) {
        return List.of();
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll().stream().map(ProductSchema::toProduct).toList();
    }
}
