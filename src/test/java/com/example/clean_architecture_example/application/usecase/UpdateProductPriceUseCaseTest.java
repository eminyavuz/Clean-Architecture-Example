package com.example.clean_architecture_example.application.usecase;

import com.example.clean_architecture_example.application.usecase.product.UpdateProductPriceUseCase;
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
public class UpdateProductPriceUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UpdateProductPriceUseCase useCase;

    @Test
    @DisplayName("Should update product price successfully when product exists")
    void should_update_product_price_successfully() {
        Product product = Product.reconstitute(1, "Keyboard", new BigDecimal("50.00"), "Description", 20, true);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        useCase.execute(1, new BigDecimal("10.00"));

        assertEquals(new BigDecimal("10.00"), product.getPrice());
        verify(productRepository).save(product);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when product does not exist")
    void should_throw_exception_when_product_not_found() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.execute(1, new BigDecimal("10.00"))
        );

        assertEquals("Product Not Found", exception.getMessage());
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when new price is negative")
    void should_throw_exception_when_new_price_is_negative() {
        Product product = Product.reconstitute(1, "Keyboard", new BigDecimal("50.00"), "Description", 20, true);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.execute(1, new BigDecimal("-10.00"))
        );

        assertEquals("Price cannot be smaller than zero", exception.getMessage());
        verify(productRepository, never()).save(any());
    }
}
