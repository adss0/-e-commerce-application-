package com.application.ecommerce.backend.usecases.product;

import com.application.ecommerce.backend.entities.product.ProductGateway;
import com.application.ecommerce.backend.entities.product.Product;
import com.application.ecommerce.backend.entities.product.ProductName;
import com.application.ecommerce.backend.entities.product.ProductPrice;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidNameException;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidPriceException;
import com.application.ecommerce.backend.usecases.product.exceptions.DuplicateNameException;

public class CreateProductUseCase {

    private final ProductGateway productGateway;

    public CreateProductUseCase(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public ProductPublicData execute(ProductCreationData newProduct) throws InvalidNameException, DuplicateNameException, InvalidPriceException {
        ProductName productName = new ProductName(newProduct.name());
        ProductPrice productPrice = new ProductPrice(newProduct.price());

        Product product = new Product(productName, productPrice);

        if(this.productGateway.checkName(product.getName())) {
            throw new DuplicateNameException(product.getName());
        }

        return new ProductPublicData(this.productGateway.create(product));
    }
}
