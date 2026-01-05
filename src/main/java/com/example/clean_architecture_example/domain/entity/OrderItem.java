package com.example.clean_architecture_example.domain.entity;

import java.math.BigDecimal;

public class OrderItem {
     private final int productId;
     private final String productName;
     private final String Description;
     private final BigDecimal unitPrice;
     private int quantity;

     public  OrderItem(Product product,int quantity)
     {
         if (product==null)
         {
             throw new IllegalArgumentException("Product cannot be null");
         }
         if (quantity<=0)
         {
             throw new IllegalArgumentException("Quantity must be bigger than zero");
         }
         this.productId=product.getId();
         this.Description=product.getDescription();
         this.quantity=quantity;
         this.unitPrice= product.getPrice();
         this.productName=product.getProductName();
     }
    public void incraseQuantity(int amount)
    {
        if ( amount<=0)
            throw new IllegalArgumentException("Amout must be positive");
        this.quantity+=amount;
    }
     public BigDecimal getTotalPrice(){
         return unitPrice.multiply(BigDecimal.valueOf(quantity));
     }
}
