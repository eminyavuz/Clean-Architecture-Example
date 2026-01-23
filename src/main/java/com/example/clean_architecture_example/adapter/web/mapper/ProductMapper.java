package com.example.clean_architecture_example.adapter.web.mapper;

import com.example.clean_architecture_example.adapter.web.dto.response.ProductResponse;
import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.infrastructure.presistence.entity.ProductJpaEntity;

public class ProductMapper {
    public static ProductResponse toResponse(ProductJpaEntity product)
    {
        ProductResponse response= new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
        return response;
    }
}
