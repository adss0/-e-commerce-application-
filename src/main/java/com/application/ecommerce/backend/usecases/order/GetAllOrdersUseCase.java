package com.application.ecommerce.backend.usecases.order;

import com.application.ecommerce.backend.entities.order.Order;
import com.application.ecommerce.backend.entities.order.OrderGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllOrdersUseCase {
    private final OrderGateway orderGateway;

    public GetAllOrdersUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public List<OrderPublicData> execute() {
        List<Order> orders = orderGateway.findAll();
        return orders.stream()
                .map(OrderPublicData::new)
                .collect(Collectors.toList());
    }
}