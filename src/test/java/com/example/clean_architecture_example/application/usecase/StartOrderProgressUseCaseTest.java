package com.example.clean_architecture_example.application.usecase;

import com.example.clean_architecture_example.application.usecase.order.StartOrderProgressUseCase;
import com.example.clean_architecture_example.domain.entity.Order;
import com.example.clean_architecture_example.domain.entity.enums.Status;
import com.example.clean_architecture_example.domain.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StartOrderProgressUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private StartOrderProgressUseCase useCase;

    @Test
    @DisplayName("Should start order progress successfully when order is in CREATED status")
    void should_start_order_progress_successfully() {
        Order order = new Order(1);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        useCase.execute(1);

        assertEquals(Status.ON_PROGRESS, order.getStatus());
        verify(orderRepository).save(order);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when order does not exist")
    void should_throw_exception_when_order_not_found() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.execute(1)
        );

        assertEquals("Order Not Found", exception.getMessage());
        verify(orderRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw IllegalStateException when order is not in CREATED status")
    void should_throw_exception_when_order_status_is_invalid() {
        Order order = Order.reconstitute(1, Status.ON_PROGRESS, List.of());
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(1)
        );

        assertEquals("Order must be on 'Created' status", exception.getMessage());
        verify(orderRepository, never()).save(any());
    }
}
