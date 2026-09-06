package com.example.clean_architecture_example.domain.exception;

public class OrderNotFoundException extends DomainException {
    public OrderNotFoundException(int orderId) {
        super("ORDER_NOT_FOUND", "Order not found with id: " + orderId);
    }
}
