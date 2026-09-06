package com.example.clean_architecture_example.application.usecase.product;

import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.domain.repository.ProductRepository;

public class GetProductUseCase {
    private final ProductRepository productRepository;
    public GetProductUseCase(ProductRepository productRepository)
    {
        this.productRepository=productRepository;
    }
    public Product execute(int productId)
    {
        return productRepository.findById(productId)
                .orElseThrow(()->new IllegalArgumentException("Product Cannot be found"));
    }
}
