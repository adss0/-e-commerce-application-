package com.application.ecommerce.backend.usecases.product;

import java.math.BigDecimal;

public record ProductCreationData(String name, BigDecimal price)
{

}
