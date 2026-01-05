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
    private final List<OrderItem> orderItems ;

    private Status status;

    // Constructors
    public Order() {

        this.createdDate = LocalDateTime.now();
        this.orderItems = new ArrayList<>();
        this.status= Status.CREATED;
    }

    // Behaviors - we add behaviors to protect the entity from invalid operations
     public void addProduct(Product product, int  quantity)
     {
         if (product== null){
             throw  new IllegalArgumentException("Product cannot be null");
         }
         if( status==Status.SHIPPED||status== Status.CANCELLED) {
             throw new IllegalStateException("Cannot add product to cancelled or shipped order ");
         }

        orderItems.add( new OrderItem(product,quantity));
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
        return orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // with copyOf usage we isolate our data from other layers
    public List<OrderItem> getOrderItems() {
        return List.copyOf(orderItems);
    }
}
