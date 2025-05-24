package com.application.ecommerce.backend.usecases.product;

import com.application.ecommerce.backend.entities.product.exceptions.InvalidIdException;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidNameException;
import com.application.ecommerce.backend.entities.product.ProductGateway;
import com.application.ecommerce.backend.entities.product.Product;
import com.application.ecommerce.backend.usecases.product.exceptions.NotFoundException;

public class GetProductUseCase {
    private final ProductGateway productGateway;

    public GetProductUseCase(ProductGateway productGateway){
        this.productGateway = productGateway;
    }

    public ProductPublicData execute(Long id) throws NotFoundException, InvalidNameException, InvalidIdException {
        Product product = this.productGateway
                .findById(id)
                .orElseThrow( () -> new NotFoundException(id));
        return new ProductPublicData(product);
    }
}
