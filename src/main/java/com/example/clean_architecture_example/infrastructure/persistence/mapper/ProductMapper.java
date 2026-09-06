package com.example.clean_architecture_example.infrastructure.persistence.mapper;

import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.infrastructure.persistence.entity.ProductJpaEntity;
import com.example.clean_architecture_example.infrastructure.persistence.repository.ProductJpaRepository;

public class ProductMapper {

    public static ProductJpaEntity toJpa(Product product)
    {
        ProductJpaEntity jpa= new ProductJpaEntity();
        jpa.setId(product.getId());
        jpa.setName(product.getProductName());
        jpa.setActive(product.isActive());
        jpa.setPrice(product.getPrice());
        jpa.setDescription(product.getDescription());
        jpa.setStock(product.getStock());
        return  jpa;
    }

    public static Product toDomain(ProductJpaEntity entity)
    {
        // domaine id içerisinde bulunan bir ctor gerekebilir.
        return Product.reconstitute(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getDescription(),
                entity.getStock(),
                entity.isActive()
        );
    }
}
