package com.example.clean_architecture_example.application.usecase.product;

import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.domain.repository.ProductRepository;

public class UpdateProductStockUseCase {
    private final ProductRepository productRepository;
    public UpdateProductStockUseCase (ProductRepository productRepository)
    {
        this.productRepository=productRepository;
    }
    public void execute(int productId,int newStock)
    {
        Product product= productRepository.findById(productId)
                .orElseThrow(()->new IllegalArgumentException("Product not found"));
        product.updateStock(newStock);
        productRepository.save(product);
    }
}
