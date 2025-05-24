package com.application.ecommerce.backend.infrastructure.gateway;

import com.application.ecommerce.backend.entities.basket.BasketGateway;
import com.application.ecommerce.backend.entities.order.OrderGateway;
import com.application.ecommerce.backend.entities.product.ProductGateway;
import com.application.ecommerce.backend.entities.promotion.PromotionGateway;
import com.application.ecommerce.backend.infrastructure.gateway.basket.BasketDatabaseGateway;
import com.application.ecommerce.backend.infrastructure.gateway.basket.BasketRepository;
import com.application.ecommerce.backend.infrastructure.gateway.order.OrderDatabaseGateway;
import com.application.ecommerce.backend.infrastructure.gateway.order.OrderRepository;
import com.application.ecommerce.backend.infrastructure.gateway.product.ProductDatabaseGateway;
import com.application.ecommerce.backend.infrastructure.gateway.product.ProductRepository;
import com.application.ecommerce.backend.infrastructure.services.creditCardService.CreditCardValidator;
import com.application.ecommerce.backend.infrastructure.services.loggerService.ApplicationLogger;
import com.application.ecommerce.backend.infrastructure.services.loggerService.Slf4jLogger;
import com.application.ecommerce.backend.usecases.basket.CreateBasketUseCase;
import com.application.ecommerce.backend.usecases.basket.DeleteBasketUseCase;
import com.application.ecommerce.backend.usecases.basket.GetBasketByCustomerIdUseCase;
import com.application.ecommerce.backend.usecases.basket.UpdateBasketUseCase;
import com.application.ecommerce.backend.usecases.order.CreateOrderUseCase;
import com.application.ecommerce.backend.usecases.order.GetAllOrdersUseCase;
import com.application.ecommerce.backend.usecases.product.CreateProductUseCase;
import com.application.ecommerce.backend.usecases.product.GetProductUseCase;
import com.application.ecommerce.backend.usecases.product.SearchProductUseCase;

import com.application.ecommerce.backend.usecases.promotion.ApplyPromotionUseCase;
import com.application.ecommerce.backend.usecases.promotion.DeletePromotionUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DatabaseConfiguration {

    // ----------- Product Use Cases -----------
    @Bean
    public CreateProductUseCase createProductUseCase(ProductRepository productRepository) {
        ProductGateway productGateway = new ProductDatabaseGateway(productRepository);
        return new CreateProductUseCase(productGateway);
    }
    @Bean
    public ProductGateway productGateway(ProductRepository productRepository) {
        return new ProductDatabaseGateway(productRepository);
    }

    @Bean
    public GetProductUseCase getProductUseCase(ProductRepository productRepository) {
        ProductGateway productGateway = new ProductDatabaseGateway(productRepository);
        return new GetProductUseCase(productGateway);
    }

    @Bean
    public SearchProductUseCase searchProductUseCase(ProductRepository productRepository) {
        ProductGateway productGateway = new ProductDatabaseGateway(productRepository);
        return new SearchProductUseCase(productGateway);
    }

    // ----------- Basket Use Cases -----------
    @Bean
    public BasketGateway basketGateway(BasketRepository basketRepository, ProductGateway productGateway, PromotionGateway promotionGateway) {
        return new BasketDatabaseGateway(basketRepository, productGateway, promotionGateway);
    }

    @Bean
    public CreateBasketUseCase createBasketUseCase(BasketGateway basketGateway) {
        return new CreateBasketUseCase(basketGateway);
    }

    @Bean
    public GetBasketByCustomerIdUseCase getBasketByCustomerIdUseCase(BasketRepository basketRepository, ProductGateway productGateway, PromotionGateway promotionGateway) {
        return new GetBasketByCustomerIdUseCase(basketRepository, productGateway, promotionGateway);
    }

    @Bean
    public UpdateBasketUseCase updateBasketUseCase(BasketRepository basketRepository, ProductGateway productGateway, PromotionGateway promotionGateway) {
        return new UpdateBasketUseCase(basketRepository, productGateway, promotionGateway);
    }

    @Bean
    public DeleteBasketUseCase deleteBasketUseCase(BasketGateway basketGateway) {
        return new DeleteBasketUseCase(basketGateway);
    }

    // ----------- Apply Promotion Use Case -----------
    @Bean
    public ApplyPromotionUseCase applyPromotionUseCase(BasketGateway basketGateway, PromotionGateway promotionGateway) {
        // ApplyPromotionUseCase no longer depends on PromotionGateway, as discounts are hardcoded.
        return new ApplyPromotionUseCase(basketGateway, promotionGateway);
    }

    @Bean
    public DeletePromotionUseCase removePromotionUseCase(BasketGateway basketGateway) {
        return new DeletePromotionUseCase(basketGateway);
    }

    // ----------- Order Use Case -----------
    @Bean
    @Primary  // Add this annotation
    public ApplicationLogger applicationLogger() {
        return new Slf4jLogger();
    }

    @Bean
    public OrderGateway orderGateway(OrderRepository orderRepository , ProductGateway productGateway, PromotionGateway promotionGateway) {
        return new OrderDatabaseGateway(orderRepository, productGateway,promotionGateway);
    }

    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderGateway orderGateway,
                                                 BasketDatabaseGateway basketGateway,
                                                 ProductGateway productGateway,
                                                 CreditCardValidator cardValidator,
                                                 DeleteBasketUseCase deleteBasketUseCase,
                                                 ApplicationLogger logger, PromotionGateway promotionGateway) {
        return new CreateOrderUseCase(orderGateway, basketGateway, productGateway, cardValidator, deleteBasketUseCase, logger, promotionGateway);
    }

    @Bean
    public GetAllOrdersUseCase getAllOrdersUseCase(OrderGateway orderGateway) {
        return new GetAllOrdersUseCase(orderGateway);
    }
}
