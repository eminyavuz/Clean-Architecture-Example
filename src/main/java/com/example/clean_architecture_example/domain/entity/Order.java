package com.example.clean_architecture_example.domain.entity;

import com.example.clean_architecture_example.domain.entity.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
     public void addProductSnapshot(int productId,String productName,String description,BigDecimal unitPrice, int  quantity)
     {
         Optional<OrderItem> existing=
                 orderItems.stream()
                         .filter(i->i.getProductId()==productId)
                         .findFirst();
         if( status==Status.SHIPPED||status== Status.CANCELLED) {
             throw new IllegalStateException("Cannot add product to cancelled or shipped order ");
         }
         if(existing.isPresent())
             existing.get().incraseQuantity(quantity);
         else
             orderItems.add( OrderItem.Create(productId,productName,description,unitPrice,quantity));
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

    public Status getStatus() {
        return status;
    }

    // with copyOf usage we isolate our data from other layers
    public List<OrderItem> getOrderItems() {
        return List.copyOf(orderItems);
    }
}
