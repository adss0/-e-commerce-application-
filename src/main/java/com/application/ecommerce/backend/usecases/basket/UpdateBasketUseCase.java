package com.application.ecommerce.backend.usecases.basket;

import com.application.ecommerce.backend.entities.basket.Basket;
import com.application.ecommerce.backend.entities.basket.BasketItem;
import com.application.ecommerce.backend.entities.basket.exceptions.InvalidBasketIdException;
import com.application.ecommerce.backend.entities.basket.exceptions.InvalidBasketItemException;
import com.application.ecommerce.backend.entities.basket.exceptions.InvalidQuantityException;
import com.application.ecommerce.backend.entities.customer.exceptions.InvalidCustomerIdException;
import com.application.ecommerce.backend.entities.product.*;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidIdException;
import com.application.ecommerce.backend.entities.product.exceptions.InvalidPriceException;
import com.application.ecommerce.backend.entities.promotion.PromotionGateway;
import com.application.ecommerce.backend.infrastructure.gateway.basket.BasketRepository;
import com.application.ecommerce.backend.infrastructure.gateway.basket.BasketSchema;
import com.application.ecommerce.backend.usecases.basket.exceptions.InvalidUseCaseException;
import com.application.ecommerce.backend.usecases.basket.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UpdateBasketUseCase {

    private final BasketRepository basketRepository;
    private final ProductGateway productGateway;
    private final PromotionGateway promotionGateway;

    public UpdateBasketUseCase(BasketRepository basketRepository, ProductGateway productGateway, PromotionGateway promotionGateway) {
        this.basketRepository = basketRepository;
        this.productGateway = productGateway;
        this.promotionGateway = promotionGateway;
    }

    public BasketPublicData execute(Long customerId, Long basketId, Long productId, int quantity, String action)
            throws InvalidCustomerIdException, InvalidIdException, InvalidPriceException, ProductNotFoundException, InvalidQuantityException, InvalidBasketItemException, InvalidBasketIdException {

        BasketSchema basketSchema = basketRepository.findById(basketId)
                .filter(basket -> basket.belongsToCustomer(customerId))
                .orElseThrow(() -> new InvalidCustomerIdException("No basket found for this customer with the provided basketId"));

        Basket basket = basketSchema.toBasket(productGateway, promotionGateway);

        ProductId pid = new ProductId(productId);

        Product product = productGateway.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(pid));

        ProductPrice price = product.getPrice();
        ProductName name = product.getName();

        switch (action.toLowerCase()) {
            case "add":
                basket.addItem(new BasketItem(pid, quantity, price, name));
                System.out.println(basket.getAppliedPromotionCode());
                break;
            case "update":
                basket.updateItemQuantity(pid, quantity);
                break;
            default:
                throw new InvalidUseCaseException();
        }

        basketRepository.save(new BasketSchema(basket));

        return new BasketPublicData(basket);
    }
}
