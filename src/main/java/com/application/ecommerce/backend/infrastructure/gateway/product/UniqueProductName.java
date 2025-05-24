package com.application.ecommerce.backend.infrastructure.gateway.product;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueProductNameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface UniqueProductName {
    String message() default "Product with name already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
