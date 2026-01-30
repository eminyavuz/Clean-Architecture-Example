package com.example.clean_architecture_example.infrastructure.presistence.mapper;

import com.example.clean_architecture_example.adapter.web.dto.response.OrderItemResponse;
import com.example.clean_architecture_example.adapter.web.dto.response.OrderResponse;
import com.example.clean_architecture_example.domain.entity.Order;
import com.example.clean_architecture_example.domain.entity.OrderItem;
import com.example.clean_architecture_example.domain.repository.OrderRepository;
import com.example.clean_architecture_example.infrastructure.presistence.entity.OrderItemJpaEntity;
import com.example.clean_architecture_example.infrastructure.presistence.entity.OrderJpaEntity;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    public static OrderJpaEntity toJpa(Order domain)
    {
        OrderJpaEntity jpaEntity= new OrderJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setStatus(domain.getStatus());
        domain.getOrderItems().forEach(item->{
                    OrderItemJpaEntity jpaItem= OrderItemMapper.toJpa(item);
                    jpaItem.setOrder(jpaEntity);
                    jpaEntity.getItems().add(jpaItem);
                });
        return jpaEntity;
    }
public static OrderResponse toResponse(OrderJpaEntity order)
{
    OrderResponse response = new OrderResponse();
    List<OrderItemResponse> items= new ArrayList<>();
    for (OrderItemJpaEntity item: order.getItems())
    {
        items.add(OrderItemMapper.toResponse(item));
    }
    response.setOrderItemResponses(items);
    response.setId(order.getId());
    response.setStatus(order.getStatus());
    return  response;
}
    public static Order toDomain(OrderJpaEntity entity)
    {
        Order order= new Order();
        order.setId(entity.getId());
        entity.getItems().forEach(i->
                order.addProductSnapshot(
                        i.getId(),
                        i.getProductName(),
                        i.getDescription(),
                        i.getPrice(),
                        i.getQuantity()
                ));
        return order;
    }
}
