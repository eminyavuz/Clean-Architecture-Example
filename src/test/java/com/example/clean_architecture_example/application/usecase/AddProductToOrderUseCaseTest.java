package com.example.clean_architecture_example.application.usecase;

import com.example.clean_architecture_example.application.usecase.order.AddProductToOrderUseCase;
import com.example.clean_architecture_example.domain.entity.Order;
import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.domain.exception.OrderNotFoundException;
import com.example.clean_architecture_example.domain.exception.ProductNotActiveException;
import com.example.clean_architecture_example.domain.repository.OrderRepository;
import com.example.clean_architecture_example.domain.repository.ProductRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddProductToOrderUseCaseTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private AddProductToOrderUseCase useCase;

    @Test
    @DisplayName("it should add a product to order, decrase stock and save it")
    void  should_add_product_to_order_successfully()
    {
        Order order= new Order(1);
        Product product= Product.reconstitute(10,"Mouse",new BigDecimal("120"),"Descriptipn",10,true);

        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(productRepository.findById(10)).thenReturn(Optional.of(product));

        useCase.execute(1,10,2);

        assertEquals(8,product.getStock());
        assertEquals(1,order.getOrderItems().size());
        verify(productRepository).save(product);
        verify(orderRepository).save(order);

    }

    @Test
    @DisplayName("should throw an exception when it can not find the order")
    void should_throw_exception_when_order_is_not_found()
    {
        when(orderRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,()-> useCase.execute(99,10,1));
        verifyNoInteractions(productRepository);
    }

    @Test
    @DisplayName("Should throw an exception when product is inactive")
    void should_throw_exception_when_product_is_inactive()
    {
        Order order = new Order(1);
        Product inactiveProduct= Product.reconstitute(10,"Mouse",new BigDecimal("150"),"Description",4,false);

        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
    when(productRepository.findById(10)).thenReturn(Optional.of(inactiveProduct));

    assertThrows(ProductNotActiveException.class,()->useCase.execute(1,10,1));

    verify(productRepository,never()).save(any());
    verify(orderRepository,never()).save(any());




}}
