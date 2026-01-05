package com.example.clean_architecture_example.domain.entity;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Product {
    private int  id;
    private String productName;
    private BigDecimal price;
    private String description;


    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Product(int id, String productName, BigDecimal price)
    {
        if (productName==null || productName.isBlank())
        {
            throw new IllegalArgumentException("Product's name cannot be empty");
        }
        if (price.compareTo(BigDecimal.ZERO)<0)
        {
            throw new IllegalArgumentException("Price cannot be smaller than zero");
        }
        this.id= id;
        this.productName=productName;
        this.price= price;
    }
    public void changePrice(BigDecimal newPrice)
    {
        if( newPrice.compareTo(BigDecimal.ZERO)<0)
        {
            throw new IllegalArgumentException("Price cannot be smaller than zero");
        }
        this.price= newPrice;
    }
}
