package com.application.ecommerce.backend.infrastructure.controller.product;

import com.application.ecommerce.backend.entities.product.exceptions.InvalidIdException;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidNameException;
import com.application.ecommerce.backend.usecases.product.exceptions.NotFoundException;
import com.application.ecommerce.backend.usecases.product.ProductPublicData;
import com.application.ecommerce.backend.usecases.product.GetProductUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GetProductController {
    private final GetProductUseCase getProductUseCase;

    public GetProductController(GetProductUseCase getProductUseCase) {
        this.getProductUseCase = getProductUseCase;
    }

    @GetMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductPublicData getProduct(@PathVariable Long id) throws NotFoundException, InvalidNameException, InvalidIdException {
        return getProductUseCase.execute(id);
    }
}
