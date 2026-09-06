package com.example.clean_architecture_example.adapter.web.dto.response;

import java.math.BigDecimal;

public class ProductResponse {
     private int id;
     private String productName;
     private String description;
     private BigDecimal price;
     private int stock;
     private boolean active;

    public ProductResponse(int id, String productName, String description, BigDecimal price, int stock, boolean active) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.active = active;
    }

    public int getId() { return id; }
    public String getProductName() { return productName; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public int getStock() { return stock; }
    public boolean isActive() { return active; }
}
