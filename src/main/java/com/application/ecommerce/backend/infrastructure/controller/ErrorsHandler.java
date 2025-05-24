package com.application.ecommerce.backend.infrastructure.controller;

import com.application.ecommerce.backend.entities.basket.exceptions.BasketItemNotFoundException;
import com.application.ecommerce.backend.entities.basket.exceptions.InvalidBasketIdException;
import com.application.ecommerce.backend.entities.basket.exceptions.InvalidBasketItemException;
import com.application.ecommerce.backend.entities.basket.exceptions.InvalidQuantityException;
import com.application.ecommerce.backend.entities.order.exceptions.InvalidOrderDateException;
import com.application.ecommerce.backend.entities.order.exceptions.InvalidOrderIdException;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidIdException;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidNameException;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidPriceException;
import com.application.ecommerce.backend.usecases.basket.exceptions.BasketNotFoundException;
import com.application.ecommerce.backend.entities.customer.exceptions.InvalidCustomerIdException;
import com.application.ecommerce.backend.usecases.basket.exceptions.InvalidUseCaseException;
import com.application.ecommerce.backend.usecases.basket.exceptions.ProductNotFoundException;
import com.application.ecommerce.backend.usecases.order.exceptions.EmptyOrderException;
import com.application.ecommerce.backend.usecases.order.exceptions.InvalidCreditCardDetailsException;
import com.application.ecommerce.backend.usecases.order.exceptions.OrderNotFoundException;
import com.application.ecommerce.backend.usecases.product.exceptions.DuplicateNameException;
import com.application.ecommerce.backend.usecases.product.exceptions.NotFoundException;
import com.application.ecommerce.backend.usecases.promotion.exceptions.InvalidPromotionCodeException;
import com.application.ecommerce.backend.usecases.promotion.exceptions.NoPromotionCodeException;
import com.application.ecommerce.backend.usecases.promotion.exceptions.PromotionAlreadyAppliedException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.*;

@RestControllerAdvice
class ErrorsHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleError400(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getFieldErrors();
        List<ValidationErrorData> messages = new ArrayList<>(errors.size());

        for (FieldError error : errors) {
            messages.add(new ValidationErrorData(error));
        }

        return ResponseEntity.badRequest().body(messages);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleError400(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(Collections.singletonMap("error", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("Invalid value for parameter '%s': '%s'. Expected type: %s",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        return ResponseEntity.badRequest().body(Collections.singletonMap("error", message));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFound(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("status", "404", "message", "Endpoint not found: " + ex.getRequestURL())
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleValidationError(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        List<ValidationErrorData> messages = new ArrayList<>();

        for (ConstraintViolation<?> violation : violations) {
            messages.add(new ValidationErrorData(
                    violation.getPropertyPath().toString(),
                    Collections.singletonList(violation.getMessage()))
            );
        }

        return ResponseEntity.badRequest().body(messages);
    }

    // === Domain Exceptions ===

    // -- Basket
    @ExceptionHandler({
            BasketNotFoundException.class,
            InvalidBasketIdException.class,
            BasketItemNotFoundException.class,
            InvalidBasketItemException.class
    })
    public ResponseEntity<?> handleBasketErrors(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<?> handleInvalidQuantity(InvalidQuantityException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(InvalidUseCaseException.class)
    public ResponseEntity<?> handleInvalidUseCase(InvalidUseCaseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("error", ex.getMessage())
        );
    }

    // -- Customer
    @ExceptionHandler(InvalidCustomerIdException.class)
    public ResponseEntity<?> handleInvalidCustomer(InvalidCustomerIdException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }

    // -- Promotion
    @ExceptionHandler(InvalidPromotionCodeException.class)
    public ResponseEntity<?> handleInvalidPromotionCode(InvalidPromotionCodeException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(NoPromotionCodeException.class)
    public ResponseEntity<?> handleNoPromotionCode(NoPromotionCodeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(PromotionAlreadyAppliedException.class)
    public ResponseEntity<?> handlePromotionAlreadyApplied(PromotionAlreadyAppliedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
    }

    // -- Product
    @ExceptionHandler({
            NotFoundException.class,
            ProductNotFoundException.class
    })
    public ResponseEntity<?> handleProductNotFound(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler({
            InvalidIdException.class,
            InvalidPriceException.class,
            InvalidNameException.class
    })
    public ResponseEntity<?> handleProductValidation(Exception ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(DuplicateNameException.class)
    public ResponseEntity<?> handleDuplicateProductName(DuplicateNameException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
    }

    // -- Order
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleOrderNotFound(OrderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(InvalidCreditCardDetailsException.class)
    public ResponseEntity<?> handleInvalidCreditCard(InvalidCreditCardDetailsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(EmptyOrderException.class)
    public ResponseEntity<?> handleEmptyOrder(EmptyOrderException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler({
            InvalidOrderIdException.class,
            InvalidOrderDateException.class
    })
    public ResponseEntity<?> handleInvalidOrderData(Exception ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    // -- General Database/System Error
    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<?> handleError500(JpaSystemException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of("error", "Database error", "message", ex.getLocalizedMessage())
        );
    }

    // === Validation Error Response Helper ===
    private record ValidationErrorData(String field, List<String> messages) {
        public ValidationErrorData(FieldError error) {
            this(error.getField(), new ArrayList<>(Collections.singletonList(error.getDefaultMessage())));
        }
    }

}
