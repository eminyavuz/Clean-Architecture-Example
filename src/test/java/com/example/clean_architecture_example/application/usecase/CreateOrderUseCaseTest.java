package com.example.clean_architecture_example.application.usecase;

import com.example.clean_architecture_example.application.usecase.order.CreateOrderUseCase;
import com.example.clean_architecture_example.domain.entity.Order;
import com.example.clean_architecture_example.domain.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateOrderUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CreateOrderUseCase useCase;

    @Test
    @DisplayName("Should create new order and return generated ID")
    void should_create_order_successfully() {
        doAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.assignId(1);
            return null;
        }).when(orderRepository).save(any(Order.class));

        int orderId = useCase.execute();

        assertEquals(1, orderId);
        verify(orderRepository).save(any(Order.class));
    }
}
