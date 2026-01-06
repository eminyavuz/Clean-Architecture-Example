package com.example.clean_architecture_example.adapter.web.dto;

import java.math.BigDecimal;

public class ProductResponse {
     private int id;
     private String productName;
     private String description;
     private BigDecimal price;

    public ProductResponse(int id, String productName, String description, BigDecimal price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
    }
}
