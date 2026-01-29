package com.example.clean_architecture_example.application.usecase.order;

import com.example.clean_architecture_example.domain.entity.Order;
import com.example.clean_architecture_example.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateOrderUseCase {
    private  final OrderRepository orderRepository;

    public CreateOrderUseCase(OrderRepository orderRepository)
    {
        this.orderRepository=orderRepository;
    }
    public int execute()
    {
        Order order= new Order();
        orderRepository.save(order);
        return order.getId();
    }
}
