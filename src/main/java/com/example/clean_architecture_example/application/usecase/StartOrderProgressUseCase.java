package com.example.clean_architecture_example.application.usecase;

import com.example.clean_architecture_example.domain.entity.Order;
import com.example.clean_architecture_example.domain.repository.OrderRepository;

public class StartOrderProgressUseCase {
    private  final OrderRepository orderRepository;
    public StartOrderProgressUseCase(OrderRepository orderRepository)
    {
        this.orderRepository=orderRepository;
    }
    public void execute(int orderId){
    Order order= orderRepository.findById(orderId).orElseThrow(()->new IllegalArgumentException("Order Not Found"));
    order.startProgress();
    orderRepository.save(order);
    }
}
