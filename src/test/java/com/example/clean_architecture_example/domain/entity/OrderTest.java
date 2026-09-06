package com.example.clean_architecture_example.domain.entity;

import  com.example.clean_architecture_example.domain.entity.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


public class OrderTest {

    @Test
    @DisplayName("New orders must be initilazed with 'Created' status")
    void  should_initialize_with_created_status()
    {
        Order  order =  new  Order();
        assertEquals(Status.CREATED,order.getStatus());
    assertTrue(order.getOrderItems().isEmpty());
    }

    @Test
    @DisplayName("product snapshot should be added to order successfully and calculate total cost right")
    void should_add_product_snapshot_and_calculate_total_price()
    {
        Order order= new Order();
        order.addProductSnapshot(1, "Mouse", "Kablosuz Mouse", new BigDecimal("50.00"), 2);
         assertEquals(1,order.getOrderItems().size());
         assertEquals(new BigDecimal("100.00"),order.getTotalPrice());
    }
    @Test
    @DisplayName("Should incrase the quantity when same product added")
    void  should_increase_quantity_when_same_product_added_again()
    {
        Order order= new Order();
        order.addProductSnapshot(1, "Mouse", "Kablosuz Mouse", new BigDecimal("50.00"), 2);
        order.addProductSnapshot(1, "Mouse", "Kablosuz Mouse", new BigDecimal("50.00"), 3);
        assertEquals(1,order.getOrderItems().size());
        assertEquals(5,order.getOrderItems().get(0).getQuantity());
        assertEquals(new BigDecimal("250.00"),order.getTotalPrice());
    }

    @Test
    @DisplayName("should start the progress of the order")
    void should_start_progress_successfully()
    {
        Order order= new Order();
        order.startProgress();
        assertEquals(Status.ON_PROGRESS,order.getStatus());
    }
}
