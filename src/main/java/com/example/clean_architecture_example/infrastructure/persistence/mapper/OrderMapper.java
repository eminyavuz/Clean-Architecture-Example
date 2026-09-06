package com.example.clean_architecture_example.infrastructure.persistence.mapper;

import com.example.clean_architecture_example.domain.entity.Order;
import com.example.clean_architecture_example.infrastructure.persistence.entity.OrderItemJpaEntity;
import com.example.clean_architecture_example.infrastructure.persistence.entity.OrderJpaEntity;

public class OrderMapper {

    public static OrderJpaEntity toJpa(Order domain) {
        OrderJpaEntity jpaEntity = new OrderJpaEntity();
        if (domain.getId() > 0) {
            jpaEntity.setId(domain.getId());
        }
        jpaEntity.setStatus(domain.getStatus());
        domain.getOrderItems().forEach(item -> {
            OrderItemJpaEntity jpaItem = OrderItemMapper.toJpa(item);
            jpaItem.setOrder(jpaEntity);
            jpaEntity.getItems().add(jpaItem);
        });
        return jpaEntity;
    }

    public static Order toDomain(OrderJpaEntity entity) {
        return Order.reconstitute(
                entity.getId(),
                entity.getStatus(),
                entity.getItems().stream().map(OrderItemMapper::toDomain).toList()
        );
    }
}
