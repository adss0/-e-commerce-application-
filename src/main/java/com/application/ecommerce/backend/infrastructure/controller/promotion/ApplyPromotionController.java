package com.application.ecommerce.backend.infrastructure.controller.promotion;

import com.application.ecommerce.backend.usecases.promotion.ApplyPromotionUseCase;
import com.application.ecommerce.backend.usecases.promotion.BasketWithDiscountData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/baskets")
public class ApplyPromotionController {

    private final ApplyPromotionUseCase applyPromotionUseCase;

    public ApplyPromotionController(ApplyPromotionUseCase applyPromotionUseCase) {
        this.applyPromotionUseCase = applyPromotionUseCase;
    }

    @PostMapping("/{basketId}/apply-promotion")
    @ResponseStatus(HttpStatus.OK)
    public BasketWithDiscountData applyPromotion(
            @PathVariable Long basketId,
            @RequestParam Long customerId,
            @RequestParam String code
    ) throws Exception {

        return applyPromotionUseCase.execute(basketId, customerId, code);
    }
}
