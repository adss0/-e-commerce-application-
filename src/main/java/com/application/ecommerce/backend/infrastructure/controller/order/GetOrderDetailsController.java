package com.application.ecommerce.backend.infrastructure.controller.order;
import com.application.ecommerce.backend.usecases.order.GetOrderByIdUseCase;
import com.application.ecommerce.backend.usecases.order.OrderPublicData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class GetOrderDetailsController {

    private final GetOrderByIdUseCase getOrderByIdUseCase;

    public GetOrderDetailsController(GetOrderByIdUseCase getOrderByIdUseCase) {
        this.getOrderByIdUseCase = getOrderByIdUseCase;
    }

    @GetMapping("/{orderId}")
    public OrderPublicData getOrderDetails(@PathVariable Long orderId) throws Exception {
        return getOrderByIdUseCase.execute(orderId);
    }
}