package com.example.clean_architecture_example.application.usecase.product;

import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActivateProductUseCase {
    private final ProductRepository productRepository;
    public ActivateProductUseCase(ProductRepository productRepository)
    {
        this.productRepository=productRepository;
    }

    public void execute(int productId)
    {
        Product product= productRepository.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException("Product cannot found"));
    product.activate();
    productRepository.save(product);
    }
}
