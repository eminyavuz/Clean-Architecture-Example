package com.example.clean_architecture_example.adapter.web.mapper;

import com.example.clean_architecture_example.adapter.web.dto.response.ProductResponse;
import com.example.clean_architecture_example.domain.entity.Product;

public class ProductMapper {
    public static ProductResponse toResponse(Product product)
    {
        ProductResponse response= new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
        return response;
    }
}
