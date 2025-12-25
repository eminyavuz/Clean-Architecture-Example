package com.example.clean_architecture_example.config;

import com.example.clean_architecture_example.application.usecase.CreateOrderUseCase;
import com.example.clean_architecture_example.domain.repository.OrderRepository;
import com.example.clean_architecture_example.infrastructure.presistence.InMemoryOrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public OrderRepository orderRepository(){
        return new InMemoryOrderRepository();
    }
    @Bean
    public CreateOrderUseCase createOrderUseCase( OrderRepository orderRepository)
    {
        return new CreateOrderUseCase(orderRepository);
    }
}
