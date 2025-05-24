package com.application.ecommerce.backend.infrastructure.controller.promotion;

import com.application.ecommerce.backend.usecases.promotion.DeletePromotionUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/baskets")
public class DeletePromotionController {

    private final DeletePromotionUseCase deletePromotionUseCase;

    // Constructor injection for DeletePromotionUseCase
    public DeletePromotionController(DeletePromotionUseCase deletePromotionUseCase) {
        this.deletePromotionUseCase = deletePromotionUseCase;
    }

    @DeleteMapping("/{basketId}/promotion")
    @ResponseStatus(HttpStatus.OK)
    public void deletePromotionCode(@PathVariable Long basketId,
                                    @RequestParam Long customerId) throws Exception {
        deletePromotionUseCase.execute(basketId, customerId);
    }
}
