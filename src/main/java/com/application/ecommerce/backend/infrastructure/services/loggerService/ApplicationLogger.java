package com.application.ecommerce.backend.infrastructure.services.loggerService;

public interface ApplicationLogger {
    void info(String message);
    void logConversion(Long basketId, Long orderId, Long customerId);
    void error(String message);

}