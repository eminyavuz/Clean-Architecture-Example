package com.example.clean_architecture_example.infrastructure.presistence.mapper;

import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.infrastructure.presistence.entity.ProductJpaEntity;
import com.example.clean_architecture_example.infrastructure.presistence.repository.ProductJpaRepository;

public class ProductMapper {

    public static ProductJpaEntity toJpa(Product product)
    {
        ProductJpaEntity jpa= new ProductJpaEntity();
        jpa.setId(product.getId());
        jpa.setName(product.getProductName());
        jpa.setActive(product.isActive());
        jpa.setPrice(product.getPrice());
        jpa.setDescription(product.getDescription());
        return  jpa;
    }

    public static Product toDomain(ProductJpaEntity entity)
    {
        return new Product(entity.getName(),entity.getPrice(),entity.getDescription(),entity.getStock(),entity.isActive());
    }
}
