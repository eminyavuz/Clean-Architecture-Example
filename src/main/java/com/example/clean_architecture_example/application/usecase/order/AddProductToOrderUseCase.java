package com.example.clean_architecture_example.application.usecase.order;
import com.example.clean_architecture_example.domain.entity.Order;
import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.domain.repository.OrderRepository;
import com.example.clean_architecture_example.domain.repository.ProductRepository;

public class AddProductToOrderUseCase {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    public AddProductToOrderUseCase(OrderRepository orderRepository,ProductRepository productRepository)
    {
        this.orderRepository= orderRepository;
        this.productRepository=productRepository;
    }
    public  void execute(int orderId, int productId,int quantity)
    {
        Order order= orderRepository.findById(orderId).orElseThrow(()->new IllegalArgumentException("Order Not Found"));
        Product product=productRepository.findById(productId).orElseThrow(()->new IllegalArgumentException("Product Not Found"));
        if(!product.isActive())
            throw new IllegalArgumentException("Product is not active");
        product.decreaseStock(quantity);
        productRepository.save(product);
        order.addProductSnapshot(
                product.getId(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
        orderRepository.save(order);
    }
}
