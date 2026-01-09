package com.example.clean_architecture_example.adapter.web.dto.request;

import java.math.BigDecimal;

public class UpdateProductPriceRequest {
    private BigDecimal newPrice;

    public BigDecimal getNewPrice() {
        return newPrice;
    }
}
