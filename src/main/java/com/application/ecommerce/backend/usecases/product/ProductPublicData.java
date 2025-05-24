package com.application.ecommerce.backend.usecases.product;

import com.application.ecommerce.backend.entities.product.Product;

public record ProductPublicData(String id, String name, String price) {

    ProductPublicData(Product product) {
        this(product.getId().asString(), product.getName().asString(), product.getPrice().asString());
    }
}