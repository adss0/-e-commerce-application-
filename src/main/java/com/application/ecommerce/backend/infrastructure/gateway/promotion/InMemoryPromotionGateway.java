package com.application.ecommerce.backend.infrastructure.gateway.promotion;

import com.application.ecommerce.backend.entities.promotion.Promotion;
import com.application.ecommerce.backend.entities.promotion.PromotionGateway;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;


@Service
public class InMemoryPromotionGateway implements PromotionGateway {

    private static final Map<String, BigDecimal> VALID_PROMOTIONS = Map.of(
            "DISCOUNT10", new BigDecimal("10.00"),
            "DISCOUNT20", new BigDecimal("20.00")
    );

    @Override
    public Optional<Promotion> findByCode(String code) {
        BigDecimal discount = VALID_PROMOTIONS.get(code.toUpperCase());
        if (discount != null) {
            return Optional.of(new Promotion(code.toUpperCase(), discount));
        }
        return Optional.empty();
    }
}

