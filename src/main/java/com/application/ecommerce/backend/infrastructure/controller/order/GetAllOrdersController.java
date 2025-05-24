package com.application.ecommerce.backend.infrastructure.controller.order;

import com.application.ecommerce.backend.usecases.order.GetAllOrdersUseCase;
import com.application.ecommerce.backend.usecases.order.OrderPublicData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class GetAllOrdersController {
    private final GetAllOrdersUseCase getAllOrdersUseCase;

    public GetAllOrdersController(GetAllOrdersUseCase getAllOrdersUseCase) {
        this.getAllOrdersUseCase = getAllOrdersUseCase;
    }

    @GetMapping
    public List<OrderPublicData> getAllOrders() {
        return getAllOrdersUseCase.execute();
    }
}