package com.example.clean_architecture_example.application.usecase;
import com.example.clean_architecture_example.domain.entity.Order;
import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.domain.repository.OrderRepository;

public class AddProductToOrderUseCase {
    private final OrderRepository orderRepository;
    public AddProductToOrderUseCase(OrderRepository orderRepository)
    {
        this.orderRepository= orderRepository;
    }
    public  void execute(int orderId, Product product,int quantity)
    {
        Order order= orderRepository.findById(orderId);
        order.addProduct(product,quantity);
        orderRepository.save(order);
    }
}
