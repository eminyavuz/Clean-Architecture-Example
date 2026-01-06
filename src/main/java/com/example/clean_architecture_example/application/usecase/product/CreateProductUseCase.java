package com.example.clean_architecture_example.application.usecase.product;

import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.domain.repository.ProductRepository;

import java.math.BigDecimal;

public class CreateProductUseCase {
    private final ProductRepository productRepository;
    public CreateProductUseCase(ProductRepository productRepository)
    {
        this.productRepository=productRepository;
    }
    public int execute(int productId,String productName, BigDecimal price, String description,int stock)
    {
        Product product= new Product(productId,productName,price,description,stock);
        productRepository.save(product);
        return product.getId();
    }
}
