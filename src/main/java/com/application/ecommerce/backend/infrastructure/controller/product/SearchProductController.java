package com.application.ecommerce.backend.infrastructure.controller.product;

import com.application.ecommerce.backend.entities.product.exceptions.InvalidIdException;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidNameException;
import com.application.ecommerce.backend.usecases.product.ProductPublicData;
import com.application.ecommerce.backend.usecases.product.SearchProductUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class SearchProductController {
    private final SearchProductUseCase searchProductUseCase;

    public SearchProductController(SearchProductUseCase searchProductUseCase){
        this.searchProductUseCase = searchProductUseCase;
    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductPublicData> searchProducts() throws InvalidNameException, InvalidIdException {
        return this.searchProductUseCase.execute();
    }
}


