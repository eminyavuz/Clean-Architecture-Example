package com.example.clean_architecture_example.domain.entity;

import com.example.clean_architecture_example.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    // This class is our main entity inner model. this class must be independent of Framework.
    private int id;
    private final LocalDateTime createdDate;
    private BigDecimal totalPrice;
    private final List<Product> products;

    private Status status;

    // Constructors
    public Order() {

        this.createdDate = LocalDateTime.now();
        this.totalPrice = BigDecimal.ZERO;
        this.products = new ArrayList<>();
        this.status= Status.CREATED;
    }

    // Behaviors - we add behaviors to protect the entity from invalid operations
     public void addProduct(Product product)
     {
         if (product== null){
             throw  new IllegalArgumentException("Product cannot be null");
         }
         if( status==Status.SHIPPED||status== Status.CANCELLED) {
             throw new IllegalStateException("Cannot add product to cancelled or shipped order ");
         }

         products.add(product);
         totalPrice = totalPrice.add(product.getPrice());
     }
     public void startProgress(){
        if (status!=Status.CREATED)
        {
            throw new IllegalStateException("Order must be on 'Created' status");
        }
        status=Status.ON_PROGRESS;
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
