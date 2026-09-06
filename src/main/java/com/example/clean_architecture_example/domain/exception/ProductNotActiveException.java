package com.example.clean_architecture_example.domain.exception;

public class ProductNotActiveException extends DomainException {
    public ProductNotActiveException(int productId) {
        super("PRODUCT_NOT_ACTIVE", "Product is not active: " + productId);
    }
}
