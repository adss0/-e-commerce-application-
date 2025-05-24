package com.application.ecommerce.backend.entities.promotion;

import java.util.Optional;

public interface PromotionGateway {
    Optional<Promotion> findByCode(String code);
}
