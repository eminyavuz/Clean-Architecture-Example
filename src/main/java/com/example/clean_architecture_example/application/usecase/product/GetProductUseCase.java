package com.example.clean_architecture_example.application.usecase.product;

import com.example.clean_architecture_example.adapter.web.dto.ProductResponse;
import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.domain.repository.ProductRepository;

public class GetProductUseCase {
    private final ProductRepository productRepository;
    public GetProductUseCase(ProductRepository productRepository)
    {
        this.productRepository=productRepository;
    }
    public ProductResponse execute(int productId)
    {
        Product product= productRepository.findById(productId)
                .orElseThrow(()->new IllegalArgumentException("Product Cannot be found"));

        return  new ProductResponse(product.getId(),product.getProductName(),product.getDescription(),product.getPrice());
    }
}
