package com.example.clean_architecture_example.application.usecase;

import com.example.clean_architecture_example.application.usecase.order.GetOrderUseCase;
import com.example.clean_architecture_example.domain.entity.Order;
import com.example.clean_architecture_example.domain.exception.OrderNotFoundException;
import com.example.clean_architecture_example.domain.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetOrderUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private GetOrderUseCase useCase;

    @Test
    @DisplayName("Should return order when order exists")
    void should_return_order_when_exists() {
        Order order = new Order(1);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        Order result = useCase.execute(1);

        assertEquals(order, result);
        verify(orderRepository).findById(1);
    }

    @Test
    @DisplayName("Should throw OrderNotFoundException when order does not exist")
    void should_throw_exception_when_order_not_found() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        OrderNotFoundException exception = assertThrows(
                OrderNotFoundException.class,
                () -> useCase.execute(1)
        );

        assertEquals("Order not found with id: 1", exception.getMessage());
    }
}
