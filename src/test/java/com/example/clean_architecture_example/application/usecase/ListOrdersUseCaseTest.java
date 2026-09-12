package com.example.clean_architecture_example.application.usecase;

import com.example.clean_architecture_example.application.usecase.order.ListOrdersUseCase;
import com.example.clean_architecture_example.domain.entity.Order;
import com.example.clean_architecture_example.domain.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListOrdersUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private ListOrdersUseCase useCase;

    @Test
    @DisplayName("Should return list of all orders from repository")
    void should_return_list_of_orders() {
        Order order = new Order(1);
        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<Order> result = useCase.execute();

        assertEquals(1, result.size());
        assertEquals(order, result.get(0));
        verify(orderRepository).findAll();
    }
}
