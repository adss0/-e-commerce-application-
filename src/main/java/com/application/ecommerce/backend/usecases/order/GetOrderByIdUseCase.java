package com.application.ecommerce.backend.usecases.order;

import com.application.ecommerce.backend.entities.order.Order;
import com.application.ecommerce.backend.entities.order.OrderGateway;
import com.application.ecommerce.backend.usecases.order.exceptions.OrderNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetOrderByIdUseCase {
    private final OrderGateway orderGateway;

    public GetOrderByIdUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public OrderPublicData execute(Long orderId) throws OrderNotFoundException {
        Order order = orderGateway.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        return new OrderPublicData(order);
    }
}
