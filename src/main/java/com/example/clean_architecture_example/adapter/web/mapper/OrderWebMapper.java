package com.example.clean_architecture_example.adapter.web.mapper;

import com.example.clean_architecture_example.adapter.web.dto.response.OrderItemResponse;
import com.example.clean_architecture_example.adapter.web.dto.response.OrderResponse;
import com.example.clean_architecture_example.domain.entity.Order;
import com.example.clean_architecture_example.domain.entity.OrderItem;

import java.util.List;

public final class OrderWebMapper {

    private OrderWebMapper() {}

    public static OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setStatus(order.getStatus());
        response.setTotalPrice(order.getTotalPrice());
        response.setOrderItemResponses(
                order.getOrderItems().stream().map(OrderWebMapper::toItemResponse).toList()
        );
        return response;
    }

    private static OrderItemResponse toItemResponse(OrderItem item) {
        OrderItemResponse response = new OrderItemResponse();
        response.setProductId(item.getProductId());
        response.setProductName(item.getProductName());
        response.setDescription(item.getDescription());
        response.setUnitPrice(item.getUnitPrice());
        response.setQuantity(item.getQuantity());
        response.setLineTotal(item.getTotalPrice());
        return response;
    }
}
