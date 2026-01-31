package com.example.clean_architecture_example.adapter.web.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class UpdateProductPriceRequest {
    @NotNull
    @Positive
    private BigDecimal newPrice;

    public BigDecimal getNewPrice() {
        return newPrice;
    }
}
