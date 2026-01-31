package com.example.clean_architecture_example.application.usecase.product;

import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.domain.repository.ProductRepository;

public class DeactivateProductUseCase {
    private final ProductRepository productRepository;
    public DeactivateProductUseCase(ProductRepository productRepository)
    {
        this.productRepository=productRepository;
    }

    public void execute(int productId){
        Product product= productRepository.findById(productId)
                .orElseThrow(()->new IllegalArgumentException("Product cannot be found"));
        product.deactivate();
        productRepository.save(product);
    }
}
