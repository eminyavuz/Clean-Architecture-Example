package com.example.clean_architecture_example.domain.exception;

public class NotEnoughStockException extends DomainException {

    public NotEnoughStockException(int productId) {
        super("NOT_ENOUGH_STOCK", "Not enough stock for product id: " + productId);
    }
}

