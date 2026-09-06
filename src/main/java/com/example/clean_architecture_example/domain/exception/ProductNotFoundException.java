package com.example.clean_architecture_example.domain.exception;

public class ProductNotFoundException extends DomainException {

    public ProductNotFoundException(int productId) {
        super("PRODUCT_NOT_FOUND", "Product not found with id: " + productId);
    }
}

