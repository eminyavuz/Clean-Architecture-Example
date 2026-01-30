package com.example.clean_architecture_example.infrastructure.presistence.mapper;

import com.example.clean_architecture_example.adapter.web.dto.response.OrderItemResponse;
import com.example.clean_architecture_example.domain.entity.OrderItem;
import com.example.clean_architecture_example.infrastructure.presistence.entity.OrderItemJpaEntity;

public class OrderItemMapper {

    public static OrderItemJpaEntity toJpa(OrderItem domain){
        OrderItemJpaEntity jpaEntity= new OrderItemJpaEntity();
        jpaEntity.setDescription(domain.getDescription());
        jpaEntity.setId(domain.getProductId());
        jpaEntity.setPrice(domain.getUnitPrice());
        jpaEntity.setQuantity(domain.getQuantity());
        jpaEntity.setProductName(domain.getProductName());

        return  jpaEntity;
    }
    public static OrderItemResponse toResponse(OrderItemJpaEntity item)
    {
        OrderItemResponse response= new OrderItemResponse();
        response.setProductId(item.getId());
        response.setUnitPrice(item.getPrice());
        response.setDescription(item.getDescription());
        response.setQuantity(item.getQuantity());
        response.setProductName(item.getProductName());
          return  response;
    }

    public  static  OrderItem toDomain(OrderItemJpaEntity entity)
    {
       return  new OrderItem(
                entity.getId(),
                entity.getProductName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getQuantity());
    }
}
