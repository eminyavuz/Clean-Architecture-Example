package com.example.clean_architecture_example.application.usecase;

import com.example.clean_architecture_example.application.usecase.product.CreateProductUseCase;
import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateProductUseCase useCase;

    @Test
    @DisplayName("Should create product and return generated ID")
    void should_create_product_successfully() {
        doAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            product.assignId(1);
            return null;
        }).when(productRepository).save(any(Product.class));

        int id = useCase.execute("Keyboard", new BigDecimal("50.00"), "Description", 20, true);

        assertEquals(1, id);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw exception when creating product with invalid parameters")
    void should_throw_exception_when_product_data_is_invalid() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.execute("Keyboard", new BigDecimal("50.00"), "Description", -5, true)
        );

        assertEquals("Stock cannot be negative", exception.getMessage());
        verify(productRepository, never()).save(any());
    }
}
