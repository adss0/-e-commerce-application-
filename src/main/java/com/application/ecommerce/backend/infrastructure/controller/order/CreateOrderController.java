package com.application.ecommerce.backend.infrastructure.controller.order;

import com.application.ecommerce.backend.infrastructure.services.creditCardService.CreditCardValidator;
import com.application.ecommerce.backend.usecases.order.CreateOrderUseCase;
import com.application.ecommerce.backend.usecases.basket.GetBasketByCustomerIdUseCase;
import com.application.ecommerce.backend.usecases.order.OrderPublicData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class CreateOrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetBasketByCustomerIdUseCase getBasketByCustomerIdUseCase;
    private final CreditCardValidator creditCardValidator;

    public CreateOrderController(CreateOrderUseCase createOrderUseCase,
                                 GetBasketByCustomerIdUseCase getBasketByCustomerIdUseCase,
                                 CreditCardValidator creditCardValidator) {
        this.createOrderUseCase = createOrderUseCase;
        this.getBasketByCustomerIdUseCase = getBasketByCustomerIdUseCase;
        this.creditCardValidator = creditCardValidator;
    }

    @PostMapping("/create")
    public String createOrder(
            @RequestParam Long customerId,
            @RequestParam Long basketId,
            @RequestParam String cardNumber,
            @RequestParam String expiryDate,
            @RequestParam String cvv) throws Exception {


        OrderPublicData order = createOrderUseCase.execute(customerId, basketId, cardNumber, expiryDate, cvv);

        return "Order created successfully. Order ID: " + order.getOrderId();
    }
}
