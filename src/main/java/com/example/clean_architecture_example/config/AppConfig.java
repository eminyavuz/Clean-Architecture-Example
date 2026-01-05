package com.example.clean_architecture_example.config;

import com.example.clean_architecture_example.application.usecase.order.AddProductToOrderUseCase;
import com.example.clean_architecture_example.application.usecase.order.CreateOrderUseCase;
import com.example.clean_architecture_example.application.usecase.order.StartOrderProgressUseCase;
import com.example.clean_architecture_example.domain.repository.OrderRepository;
import com.example.clean_architecture_example.domain.repository.ProductRepository;
import com.example.clean_architecture_example.infrastructure.presistence.InMemoryOrderRepository;
import com.example.clean_architecture_example.infrastructure.presistence.InMemoryProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public OrderRepository orderRepository(){
        return new InMemoryOrderRepository();
    }
    @Bean
    public ProductRepository productRepository()
    {
        return  new InMemoryProductRepository();
    }
    @Bean
    public CreateOrderUseCase createOrderUseCase( OrderRepository orderRepository)
    {
        return new CreateOrderUseCase(orderRepository);
    }
    @Bean
    public AddProductToOrderUseCase addProductToOrderUseCase(OrderRepository orderRepository,ProductRepository productRepository) {
        return new AddProductToOrderUseCase(orderRepository,productRepository);
    }

    @Bean
    public StartOrderProgressUseCase startOrderProgressUseCase(OrderRepository orderRepository) {
        return new StartOrderProgressUseCase(orderRepository);
    }
}
