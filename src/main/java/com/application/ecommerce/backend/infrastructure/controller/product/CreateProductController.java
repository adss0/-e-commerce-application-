package com.application.ecommerce.backend.infrastructure.controller.product;

import com.application.ecommerce.backend.entities.product.exceptions.InvalidNameException;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidPriceException;
import com.application.ecommerce.backend.usecases.product.exceptions.DuplicateNameException;
import com.application.ecommerce.backend.usecases.product.ProductCreationData;
import com.application.ecommerce.backend.usecases.product.ProductPublicData;
import com.application.ecommerce.backend.usecases.product.CreateProductUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CreateProductController {
    private final CreateProductUseCase createProductUseCase;

    public CreateProductController(CreateProductUseCase createProductUseCase){
        this.createProductUseCase = createProductUseCase;
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductPublicData createProduct(@Valid @RequestBody ProductCreationData newProduct) throws InvalidNameException, DuplicateNameException, InvalidPriceException {
        return createProductUseCase.execute(newProduct);
    }
}
