package com.example.clean_architecture_example.adapter.web.dto.response;

import com.example.clean_architecture_example.domain.entity.enums.Status;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponse {
    private int id;
    private List<OrderItemResponse> orderItemResponses;
    private Status status;
    private BigDecimal totalPrice;

    public int getId() {
        return id;
    }

    public List<OrderItemResponse> getOrderItemResponses() {
        return orderItemResponses;
    }

    public Status getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setOrderItemResponses(List<OrderItemResponse> orderItemResponses) {
        this.orderItemResponses = orderItemResponses;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
