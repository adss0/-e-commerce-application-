package com.application.ecommerce.backend.infrastructure.services.creditCardService;


import com.application.ecommerce.backend.usecases.order.exceptions.InvalidCreditCardDetailsException;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class CreditCardValidationService implements CreditCardValidator {

    @Override
    public boolean validateCardDetails(String cardNumber, String expiryDate, String cvv) throws InvalidCreditCardDetailsException {
        if (!isValidCardNumber(cardNumber)) {
            throw new InvalidCreditCardDetailsException("Invalid card number");
        }
        if (!isValidExpiryDate(expiryDate)) {
            throw new InvalidCreditCardDetailsException("Invalid or expired expiry date");
        }
        if (!isValidCVV(cvv)) {
            throw new InvalidCreditCardDetailsException("Invalid CVV");
        }
        return true;
    }

    private boolean isValidCardNumber(String cardNumber) {
        if (cardNumber == null || !cardNumber.matches("\\d{13,19}")) return false;
        return luhnCheck(cardNumber);
    }

    private boolean isValidExpiryDate(String expiryDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth exp = YearMonth.parse(expiryDate, formatter);
            return !exp.isBefore(YearMonth.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidCVV(String cvv) {
        return cvv != null && cvv.matches("\\d{3,4}");
    }

    private boolean luhnCheck(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) n -= 9;
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}