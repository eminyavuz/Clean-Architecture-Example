package com.example.clean_architecture_example.application.usecase;

import com.example.clean_architecture_example.domain.repository.OrderRepository;
import com.example.clean_architecture_example.domain.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;

public class AddProductToOrderUseCaseTest {
    private final OrderRepository  orderRepository;
    private final ProductRepository productRepository;

    public AddProductToOrderUseCaseTest(OrderRepository orderRepository,ProductRepository productRepository)
    {
        this.orderRepository=orderRepository;
        this.productRepository=productRepository;
    }



}
