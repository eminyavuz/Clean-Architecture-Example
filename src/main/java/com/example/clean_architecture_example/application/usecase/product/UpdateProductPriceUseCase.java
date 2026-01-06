package com.example.clean_architecture_example.application.usecase.product;

import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.domain.repository.ProductRepository;

import java.math.BigDecimal;

public class UpdateProductPriceUseCase {
    private  final ProductRepository productRepository;
    public UpdateProductPriceUseCase(ProductRepository productRepository)
    {
        this.productRepository=productRepository;
    }
    public void execute(int productId, BigDecimal newPrice)
    {
        Product product= productRepository.findById(productId)
                .orElseThrow(()->new IllegalArgumentException("Product Not Found"));
    product.changePrice(newPrice);
    productRepository.save(product);
    }
}
