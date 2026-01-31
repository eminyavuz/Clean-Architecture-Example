package com.example.clean_architecture_example.domain.entity;

import java.math.BigDecimal;

public class Product {
    private int  id;
    private String productName;
    private BigDecimal price;
    private String description;
    private int  stock;
    private boolean isActive;



    public int getId() {
        return id;
    }
    public  boolean isActive(){
        return this.isActive;
    }

    public int getStock() {
        return stock;
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

    public Product(String productName, BigDecimal price, String description, int stock, Boolean isActive )
    {
        if (productName==null || productName.isBlank())
        {
            throw new IllegalArgumentException("Product's name cannot be empty");
        }
        if (price.compareTo(BigDecimal.ZERO)<0)
        {
            throw new IllegalArgumentException("Price cannot be smaller than zero");
        }
        if(description==null || description.length()>255)
        {
            throw new IllegalArgumentException("Description cannot be empty or more than 255 characters");
        }
        if (stock<0 )
        {
            throw  new IllegalArgumentException("Stock cannot be negative");
        }

        this.isActive = isActive != null ? isActive : true;
        this.productName=productName;
        this.price= price;
        this.description= description;
        this.stock= stock;
    }
    public void changePrice(BigDecimal newPrice)
    {
        if( newPrice.compareTo(BigDecimal.ZERO)<0)
        {
            throw new IllegalArgumentException("Price cannot be smaller than zero");
        }
        this.price= newPrice;
    }
    public void deactivate()
    {
        this.isActive=false;
    }

    public void  decreaseStock(int quantity)
    {
        if (quantity<=0)
        {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (stock<quantity)
        {
            throw new IllegalArgumentException("not enough stock");
        }
        stock-=quantity;
    }

    public void updateStock(int newStock) {
        if(newStock<0)
            throw new IllegalArgumentException("Stock cannot be less than zero");
        stock=newStock;
    }

    public void activate() {
        this.isActive=true;
    }
}
