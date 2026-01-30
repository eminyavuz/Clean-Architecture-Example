package com.example.clean_architecture_example.adapter.web.dto.response;

import java.math.BigDecimal;

public class OrderItemResponse {
    private  int productId;
    private String productName;
    private  String Description;
    private BigDecimal unitPrice;
    private int quantity;

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return Description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
