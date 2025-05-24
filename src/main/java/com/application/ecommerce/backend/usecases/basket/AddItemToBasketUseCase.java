package com.application.ecommerce.backend.usecases.basket;

import com.application.ecommerce.backend.entities.basket.Basket;
import com.application.ecommerce.backend.entities.basket.BasketGateway;
import com.application.ecommerce.backend.entities.basket.BasketId;
import com.application.ecommerce.backend.entities.basket.BasketItem;
import com.application.ecommerce.backend.entities.customer.CustomerId;
import com.application.ecommerce.backend.entities.customer.exceptions.InvalidCustomerIdException;
import com.application.ecommerce.backend.entities.product.ProductGateway;
import com.application.ecommerce.backend.entities.product.Product;
import com.application.ecommerce.backend.usecases.basket.exceptions.BasketNotFoundException;
import com.application.ecommerce.backend.usecases.basket.exceptions.ProductNotFoundException;

public class AddItemToBasketUseCase {
    private final BasketGateway basketGateway;
    private final ProductGateway productGateway;

    public AddItemToBasketUseCase(BasketGateway basketGateway, ProductGateway productGateway) {
        this.basketGateway = basketGateway;
        this.productGateway = productGateway;
    }

    public Basket execute(BasketId basketId, CustomerId customerId, BasketItem item) throws InvalidCustomerIdException, ProductNotFoundException {

        Product product = productGateway.findById(item.getProductId().asLong())
                .orElseThrow(() -> new ProductNotFoundException(item.getProductId())); // If product doesn't exist, throw exception

        // retrieve the basket and add the item
        Basket basket = basketGateway.findById(basketId, customerId)
                .orElseThrow(() -> new BasketNotFoundException(basketId.asLong()));

        basket.addItem(item);

        return basketGateway.update(basket);
    }
}
