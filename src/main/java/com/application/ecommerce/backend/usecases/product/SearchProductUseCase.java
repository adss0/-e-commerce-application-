package com.application.ecommerce.backend.usecases.product;

import com.application.ecommerce.backend.entities.product.ProductGateway;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidIdException;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidNameException;

import java.util.List;

public class SearchProductUseCase {
    private final ProductGateway productGateway;

    public SearchProductUseCase(ProductGateway productGateway){
        this.productGateway = productGateway;
    }

    public List<ProductPublicData> execute() throws InvalidNameException, InvalidIdException {
        return this.productGateway.findAll().stream().map(ProductPublicData::new).toList();
    }
}
