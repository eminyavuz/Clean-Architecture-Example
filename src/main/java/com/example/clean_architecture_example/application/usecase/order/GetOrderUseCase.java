package com.example.clean_architecture_example.application.usecase.order;

import com.example.clean_architecture_example.domain.entity.Order;
import com.example.clean_architecture_example.domain.exception.OrderNotFoundException;
import com.example.clean_architecture_example.domain.repository.OrderRepository;

public class GetOrderUseCase {
    private final OrderRepository orderRepository;

    public GetOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order execute(int orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
