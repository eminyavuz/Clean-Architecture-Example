package com.example.clean_architecture_example.config;

import com.example.clean_architecture_example.application.usecase.order.AddProductToOrderUseCase;
import com.example.clean_architecture_example.application.usecase.order.CreateOrderUseCase;
import com.example.clean_architecture_example.application.usecase.order.GetOrderUseCase;
import com.example.clean_architecture_example.application.usecase.order.ListOrdersUseCase;
import com.example.clean_architecture_example.application.usecase.order.StartOrderProgressUseCase;
import com.example.clean_architecture_example.application.usecase.product.*;
import com.example.clean_architecture_example.domain.repository.OrderItemRepository;
import com.example.clean_architecture_example.domain.repository.OrderRepository;
import com.example.clean_architecture_example.domain.repository.ProductRepository;
import com.example.clean_architecture_example.infrastructure.persistence.jpa.adapter.JpaOrderItemAdapter;
import com.example.clean_architecture_example.infrastructure.persistence.jpa.adapter.JpaOrderRepositoryAdapter;
import com.example.clean_architecture_example.infrastructure.persistence.jpa.adapter.JpaProductRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // Use case Definitions
    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderRepository orderRepository) {
        return new CreateOrderUseCase(orderRepository);
    }

    @Bean
    public AddProductToOrderUseCase addProductToOrderUseCase(OrderRepository orderRepository, ProductRepository productRepository) {
        return new AddProductToOrderUseCase(orderRepository, productRepository);
    }

    @Bean
    public StartOrderProgressUseCase startOrderProgressUseCase(OrderRepository orderRepository) {
        return new StartOrderProgressUseCase(orderRepository);
    }

    @Bean
    public GetOrderUseCase getOrderUseCase(OrderRepository orderRepository) {
        return new GetOrderUseCase(orderRepository);
    }

    @Bean
    public ListOrdersUseCase listOrdersUseCase(OrderRepository orderRepository) {
        return new ListOrdersUseCase(orderRepository);
    }

    @Bean
    public ActivateProductUseCase activateProductUseCase(ProductRepository productRepository) {
        return new ActivateProductUseCase(productRepository);
    }

    @Bean
    public CreateProductUseCase createProductUseCase(ProductRepository productRepository) {
        return new CreateProductUseCase(productRepository);
    }

    @Bean
    public DeactivateProductUseCase deactivateProductUseCase(ProductRepository productRepository) {
        return new DeactivateProductUseCase(productRepository);
    }

    @Bean
    public GetProductUseCase getProductUseCase(ProductRepository productRepository) {
        return new GetProductUseCase(productRepository);
    }

    @Bean
    public ListProductsUseCase listProductsUseCase(ProductRepository productRepository) {
        return new ListProductsUseCase(productRepository);
    }

    @Bean
    public UpdateProductPriceUseCase updateProductPriceUseCase(ProductRepository productRepository) {
        return new UpdateProductPriceUseCase(productRepository);
    }

    @Bean
    public UpdateProductStockUseCase updateProductStockUseCase(ProductRepository productRepository) {
        return new UpdateProductStockUseCase(productRepository);
    }

    // Repository Definitions
    @Bean
    ProductRepository productRepository(JpaProductRepositoryAdapter adapter) {
        return adapter;
    }

    @Bean
    OrderRepository orderRepository(JpaOrderRepositoryAdapter adapter) {
        return adapter;
    }

    @Bean
    OrderItemRepository orderItemRepository(JpaOrderItemAdapter adapter) {
        return adapter;
    }
}
