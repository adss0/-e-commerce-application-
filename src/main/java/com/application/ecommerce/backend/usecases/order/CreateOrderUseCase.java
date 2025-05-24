package com.application.ecommerce.backend.usecases.order;

import com.application.ecommerce.backend.entities.basket.*;
import com.application.ecommerce.backend.entities.basket.exceptions.InvalidBasketIdException;
import com.application.ecommerce.backend.entities.customer.exceptions.InvalidCustomerIdException;
import com.application.ecommerce.backend.entities.order.*;
import com.application.ecommerce.backend.entities.order.exceptions.InvalidOrderDateException;
import com.application.ecommerce.backend.entities.product.*;
import com.application.ecommerce.backend.entities.customer.*;
import com.application.ecommerce.backend.entities.promotion.Promotion;
import com.application.ecommerce.backend.entities.promotion.PromotionGateway;
import com.application.ecommerce.backend.infrastructure.gateway.basket.BasketDatabaseGateway;
import com.application.ecommerce.backend.infrastructure.services.creditCardService.CreditCardValidator;
import com.application.ecommerce.backend.infrastructure.services.loggerService.ApplicationLogger;
import com.application.ecommerce.backend.usecases.basket.*;
import com.application.ecommerce.backend.usecases.basket.exceptions.BasketNotFoundException;
import com.application.ecommerce.backend.usecases.order.exceptions.EmptyOrderException;
import com.application.ecommerce.backend.usecases.order.exceptions.InvalidCreditCardDetailsException;
import com.application.ecommerce.backend.usecases.promotion.exceptions.InvalidPromotionCodeException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CreateOrderUseCase {
    private final OrderGateway orderGateway;
    private final BasketDatabaseGateway basketGateway;
    private final ProductGateway productGateway;
    private final CreditCardValidator cardValidator;
    private final DeleteBasketUseCase deleteBasketUseCase;
    private final ApplicationLogger logger;
    private final PromotionGateway promotionGateway;

    public CreateOrderUseCase(OrderGateway orderGateway,
                              BasketDatabaseGateway basketGateway,
                              ProductGateway productGateway,
                              CreditCardValidator cardValidator,
                              DeleteBasketUseCase deleteBasketUseCase,
                              ApplicationLogger logger, PromotionGateway promotionGateway) {
        this.orderGateway = orderGateway;
        this.basketGateway = basketGateway;
        this.productGateway = productGateway;
        this.cardValidator = cardValidator;
        this.deleteBasketUseCase = deleteBasketUseCase;
        this.logger = logger;
        this.promotionGateway = promotionGateway;
    }

    @Transactional
    public OrderPublicData execute(Long customerId, Long basketId,
                                   String cardNumber, String expiryDate, String cvv)
            throws InvalidCustomerIdException, EmptyOrderException,
            InvalidBasketIdException, InvalidCreditCardDetailsException,
            InvalidOrderDateException, InvalidPromotionCodeException {

        logger.info("Order creation started for basket: " + basketId);
        validateInputs(customerId, basketId, cardNumber, expiryDate, cvv);

        Basket basket = getValidatedBasket(basketId, customerId);
        Order order = createOrderFromBasket(basket, customerId);

        orderGateway.save(order);
        logger.logConversion(basketId, order.getId().asLong(), customerId);

        cleanupBasket(basketId, customerId);
        logger.info("Order created Successfully");
        return new OrderPublicData(order);
    }

    private void validateInputs(Long customerId, Long basketId,
                                String cardNumber, String expiryDate, String cvv)
            throws InvalidCreditCardDetailsException {
        if (!cardValidator.validateCardDetails(cardNumber, expiryDate, cvv)) {
            throw new InvalidCreditCardDetailsException("Invalid card details");
        }
    }

    private Basket getValidatedBasket(Long basketId, Long customerId) throws InvalidBasketIdException, InvalidCustomerIdException, EmptyOrderException {
        Basket basket = basketGateway.findById(new BasketId(basketId), new CustomerId(customerId))
                .orElseThrow(() -> new RuntimeException("Basket not found"));

        if (basket.getItems().isEmpty()) {
            throw new EmptyOrderException();
        }

        basket.getItems().forEach(item -> {
            if (item.getQuantity() <= 0) {
                throw new IllegalArgumentException("Invalid quantity for product " + item.getProductId());
            }
        });

        return basket;
    }

    private Order createOrderFromBasket(Basket basket, Long customerId) throws InvalidOrderDateException, InvalidCustomerIdException {
        List<Product> products = convertItemsToProducts(basket);
        Map<ProductId, Integer> quantities = getProductQuantities(basket);

        BigDecimal discountPercentage = null;
        if (basket.getAppliedPromotionCode() != null) {
            discountPercentage = promotionGateway.findByCode(basket.getAppliedPromotionCode())
                    .map(Promotion::getDiscountPercent)
                    .orElse(null);
        }

        return new Order(
                null,
                new CustomerId(customerId),
                new OrderDate(LocalDateTime.now()),
                products,
                quantities,
                basket.getAppliedPromotionCode(),
                basket.calculateTotalWithoutPromotion(),
                basket.calculateTotalWithPromotion(discountPercentage)
        );
    }

    private List<Product> convertItemsToProducts(Basket basket) {
        return basket.getItems().stream()
                .map(item -> productGateway.findById(item.getProductId().asLong())
                        .orElseThrow(() -> new RuntimeException("Product not found")))
                .collect(Collectors.toList());
    }

    private Map<ProductId, Integer> getProductQuantities(Basket basket) {
        return basket.getItems().stream()
                .collect(Collectors.toMap(
                        BasketItem::getProductId,
                        BasketItem::getQuantity
                ));
    }

    private void cleanupBasket(Long basketId, Long customerId) throws InvalidBasketIdException, InvalidCustomerIdException {
        try {
            deleteBasketUseCase.execute(new BasketId(basketId), new CustomerId(customerId));
        } catch (BasketNotFoundException e) {
            logger.error("Basket cleanup failed: " + e.getMessage());
        }
    }
}