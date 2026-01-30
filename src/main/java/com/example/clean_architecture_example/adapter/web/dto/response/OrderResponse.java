package com.example.clean_architecture_example.adapter.web.dto.response;

import com.example.clean_architecture_example.enums.Status;

import java.util.List;

public class OrderResponse {
    int id ;
    private List<OrderItemResponse> orderItemResponses;
    private Status status;

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
}
