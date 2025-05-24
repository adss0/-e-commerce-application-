package com.application.ecommerce.backend.infrastructure.services.creditCardService;

import com.application.ecommerce.backend.usecases.order.exceptions.InvalidCreditCardDetailsException;

public interface CreditCardValidator {
     boolean validateCardDetails(String cardNumber, String expiryDate, String cvv) throws InvalidCreditCardDetailsException;
}
