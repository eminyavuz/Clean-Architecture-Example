package com.example.clean_architecture_example.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    // This class is our main entity inner model. this class must be independed from  Framework.
    private int id;
    private final LocalDateTime createdDate;
    private BigDecimal totalPrice;
    private final List<Product> products;

    // Constructors
    public Order() {

        this.createdDate = LocalDateTime.now();
        this.totalPrice = BigDecimal.ZERO;
        this.products = new ArrayList<>();
    }

    // Behaviors - we add behaviors to protect the entity from invalid operations
     public void addProduct(Product product)
     {
         if (product== null){
             throw  new IllegalArgumentException("Product cannot be null");
         }
         products.add(product);
         totalPrice = totalPrice.add(product.getPrice());

     }

    //Getters
    public int getId() {
        return id;
    }


    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    // with this usage we isolate our data from outerworld
    public List<Product> getProducts() {
        return List.copyOf(products);
    }
}
