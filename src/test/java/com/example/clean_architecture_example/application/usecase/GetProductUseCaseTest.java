package com.example.clean_architecture_example.application.usecase;

import com.example.clean_architecture_example.application.usecase.product.GetProductUseCase;
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
public class GetProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductUseCase useCase;

    @Test
    @DisplayName("Should return product when product exists")
    void should_return_product_when_exists() {
        Product product = Product.reconstitute(1, "Keyboard", new BigDecimal("50.00"), "Description", 20, true);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Product result = useCase.execute(1);

        assertEquals(product, result);
        verify(productRepository).findById(1);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when product does not exist")
    void should_throw_exception_when_product_not_found() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.execute(1)
        );

        assertEquals("Product Cannot be found", exception.getMessage());
    }
}
