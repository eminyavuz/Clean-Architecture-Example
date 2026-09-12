package com.example.clean_architecture_example.application.usecase;

import com.example.clean_architecture_example.application.usecase.product.UpdateProductStockUseCase;
import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateProductStockUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UpdateProductStockUseCase useCase;

    @Test
    @DisplayName("Should update product stock successfully when product exists")
    void should_update_product_stock_successfully() {
        Product product = Product.reconstitute(1, "Keyboard", new BigDecimal("50.00"), "Description", 20, true);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        useCase.execute(1, 10);

        assertEquals(10, product.getStock());
        verify(productRepository).save(product);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when product does not exist")
    void should_throw_exception_when_product_not_found() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.execute(1, 10)
        );

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when stock is negative")
    void should_throw_exception_when_new_stock_is_negative() {
        Product product = Product.reconstitute(1, "Keyboard", new BigDecimal("50.00"), "Description", 20, true);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.execute(1, -5)
        );

        assertEquals("Stock cannot be less than zero", exception.getMessage());
        verify(productRepository, never()).save(any());
    }
}
