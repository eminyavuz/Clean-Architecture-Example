package com.example.clean_architecture_example.domain.entity;

import java.math.BigDecimal;

public class OrderItem {
     private final int productId;
     private final String productName;
     private final String Description;
     private final BigDecimal unitPrice;
     private int quantity;


    public OrderItem(int productId,
             String productName,
             String description,
             BigDecimal unitPrice
             ,int quantity)
     {
         if (quantity<=0)
         {
             throw new IllegalArgumentException("Quantity must be bigger than zero");
         }
         this.productId=productId;
         this.Description=description;
         this.quantity=quantity;
         this.unitPrice= unitPrice;
         this.productName=productName;
     }
public static OrderItem Create(
        int productId,
        String productName,
        String description,
        BigDecimal unitPrice,
        int quantity
)
{
    return new OrderItem(productId, productName, description, unitPrice,quantity);
}

    public void incraseQuantity(int amount)
    {
        if ( amount<=0)
            throw new IllegalArgumentException("Amount must be positive");
        this.quantity+=amount;
    }
     public BigDecimal getTotalPrice(){
         return unitPrice.multiply(BigDecimal.valueOf(quantity));
     }

     //getters
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
}
