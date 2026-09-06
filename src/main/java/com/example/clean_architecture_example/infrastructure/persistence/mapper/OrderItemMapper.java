package com.example.clean_architecture_example.infrastructure.persistence.mapper;

import com.example.clean_architecture_example.domain.entity.OrderItem;
import com.example.clean_architecture_example.infrastructure.persistence.entity.OrderItemJpaEntity;

public class OrderItemMapper {

    public static OrderItemJpaEntity toJpa(OrderItem domain) {
        OrderItemJpaEntity jpaEntity = new OrderItemJpaEntity();
        jpaEntity.setProductId(domain.getProductId());
        jpaEntity.setDescription(domain.getDescription());
        jpaEntity.setPrice(domain.getUnitPrice());
        jpaEntity.setQuantity(domain.getQuantity());
        jpaEntity.setProductName(domain.getProductName());
        return jpaEntity;
    }

    public static OrderItem toDomain(OrderItemJpaEntity entity) {
        return OrderItem.Create(
                entity.getProductId(),
                entity.getProductName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getQuantity()
        );
    }
}
