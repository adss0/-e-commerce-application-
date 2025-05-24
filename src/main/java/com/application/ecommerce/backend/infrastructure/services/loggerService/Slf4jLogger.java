package com.application.ecommerce.backend.infrastructure.services.loggerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;


@Service
public class Slf4jLogger implements ApplicationLogger {
    private final Logger logger;

    public Slf4jLogger() {
        this.logger = LoggerFactory.getLogger(Slf4jLogger.class);
    }

    @Override
    public void info(String message) {
        logger.info("[INFO] {}", message);
    }

    @Override
    public void logConversion(Long basketId, Long orderId, Long customerId) {
        logger.info("[CONVERSION] BasketID: {} → OrderID: {} | CustomerID: {}",
                basketId, orderId, customerId);
    }

    @Override
    public void error(String message) {
        logger.error("[ERROR] {}", message);
    }

}