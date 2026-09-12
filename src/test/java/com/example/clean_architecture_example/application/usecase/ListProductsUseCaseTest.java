package com.example.clean_architecture_example.application.usecase;

import com.example.clean_architecture_example.application.usecase.product.ListProductsUseCase;
import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListProductsUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ListProductsUseCase useCase;

    @Test
    @DisplayName("Should return list of all products from repository")
    void should_return_list_of_products() {
        Product product = Product.reconstitute(1, "Keyboard", new BigDecimal("50.00"), "Description", 20, true);
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> result = useCase.execute();

        assertEquals(1, result.size());
        assertEquals(product, result.get(0));
        verify(productRepository).findAll();
    }
}
