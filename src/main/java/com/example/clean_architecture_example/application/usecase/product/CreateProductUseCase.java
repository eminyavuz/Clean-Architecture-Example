package com.example.clean_architecture_example.application.usecase.product;

import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


public class CreateProductUseCase {
    private final ProductRepository productRepository;
    public CreateProductUseCase(ProductRepository productRepository)
    {
        this.productRepository=productRepository;
    }
    public int execute(String productName, BigDecimal price, String description,int stock,boolean isActive)
    {
        Product product= new Product(productName,price,description,stock,isActive);
        productRepository.save(product);
        return product.getId();
    }
}
