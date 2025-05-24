package com.application.ecommerce.backend.infrastructure.gateway.product;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, String> {
    @Autowired
    private ProductRepository repository;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context){
        if (repository != null) {
            Optional<ProductSchema> optional = repository.findByName(name);
            return optional.isEmpty();
        }

        return true;
    }
}
