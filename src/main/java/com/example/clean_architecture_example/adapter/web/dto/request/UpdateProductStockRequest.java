package com.example.clean_architecture_example.adapter.web.dto.request;

import jakarta.validation.constraints.Positive;

public class UpdateProductStockRequest {
    @Positive
    private int newStock;

    public int getNewStock() {
        return newStock;
    }
}
