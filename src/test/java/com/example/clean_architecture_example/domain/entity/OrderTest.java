package com.example.clean_architecture_example.domain.entity;

import com.example.clean_architecture_example.domain.entity.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    @DisplayName("New orders must be initialized with 'CREATED' status")
    void should_initialize_with_created_status() {
        Order order = new Order();
        assertEquals(Status.CREATED, order.getStatus());
        assertTrue(order.getOrderItems().isEmpty());
    }

    @Test
    @DisplayName("Product snapshot should be added to order successfully and calculate total price correctly")
    void should_add_product_snapshot_and_calculate_total_price() {
        Order order = new Order();
        order.addProductSnapshot(1, "Mouse", "Kablosuz Mouse", new BigDecimal("50.00"), 2);
        assertEquals(1, order.getOrderItems().size());
        assertEquals(new BigDecimal("100.00"), order.getTotalPrice());
    }

    @Test
    @DisplayName("Should increase the quantity when same product is added again")
    void should_increase_quantity_when_same_product_added_again() {
        Order order = new Order();
        order.addProductSnapshot(1, "Mouse", "Kablosuz Mouse", new BigDecimal("50.00"), 2);
        order.addProductSnapshot(1, "Mouse", "Kablosuz Mouse", new BigDecimal("50.00"), 3);
        assertEquals(1, order.getOrderItems().size());
        assertEquals(5, order.getOrderItems().get(0).getQuantity());
        assertEquals(new BigDecimal("250.00"), order.getTotalPrice());
    }

    @Test
    @DisplayName("Should start the progress of the order successfully")
    void should_start_progress_successfully() {
        Order order = new Order();
        order.startProgress();
        assertEquals(Status.ON_PROGRESS, order.getStatus());
    }

    // --- Implemented Test Scenarios ---

    @Test
    @DisplayName("Should throw IllegalStateException when adding product to shipped or cancelled order")
    void should_throw_exception_when_adding_product_to_shipped_or_cancelled_order() {
        Order shippedOrder = Order.reconstitute(1, Status.SHIPPED, List.of());
        IllegalStateException exception1 = assertThrows(
                IllegalStateException.class,
                () -> shippedOrder.addProductSnapshot(1, "Mouse", "Kablosuz Mouse", new BigDecimal("50.00"), 2)
        );
        assertEquals("Cannot add product to cancelled or shipped order ", exception1.getMessage());

        Order cancelledOrder = Order.reconstitute(2, Status.CANCELLED, List.of());
        IllegalStateException exception2 = assertThrows(
                IllegalStateException.class,
                () -> cancelledOrder.addProductSnapshot(1, "Mouse", "Kablosuz Mouse", new BigDecimal("50.00"), 2)
        );
        assertEquals("Cannot add product to cancelled or shipped order ", exception2.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalStateException when starting progress on an order not in CREATED status")
    void should_throw_exception_when_starting_progress_on_non_created_order() {
        Order order = Order.reconstitute(1, Status.ON_PROGRESS, List.of());
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                order::startProgress
        );
        assertEquals("Order must be on 'Created' status", exception.getMessage());
    }

    @Test
    @DisplayName("Should assign ID when ID is zero")
    void should_assign_id_successfully() {
        Order order = new Order();
        order.assignId(1);
        assertEquals(1, order.getId());
    }

    @Test
    @DisplayName("Should throw exception when assigning non-positive ID")
    void should_throw_exception_when_assigning_invalid_id() {
        Order order = new Order();
        IllegalArgumentException exception1 = assertThrows(
                IllegalArgumentException.class,
                () -> order.assignId(-1)
        );
        assertEquals("Id must be greater than zero", exception1.getMessage());

        IllegalArgumentException exception2 = assertThrows(
                IllegalArgumentException.class,
                () -> order.assignId(0)
        );
        assertEquals("Id must be greater than zero", exception2.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when creating order with non-positive ID")
    void should_throw_exception_when_creating_order_with_invalid_id() {
        IllegalArgumentException exception1 = assertThrows(
                IllegalArgumentException.class,
                () -> new Order(-1)
        );
        assertEquals("Id must be greater than zero", exception1.getMessage());

        IllegalArgumentException exception2 = assertThrows(
                IllegalArgumentException.class,
                () -> new Order(0)
        );
        assertEquals("Id must be greater than zero", exception2.getMessage());
    }

    @Test
    @DisplayName("Should reconstitute order from persistence data")
    void should_reconstitute_order_successfully() {
        List<OrderItem> items = List.of(OrderItem.Create(1, "Mouse", "Kablosuz Mouse", new BigDecimal("50.00"), 2));
        Order reconstituted = Order.reconstitute(10, Status.ON_PROGRESS, items);

        assertEquals(10, reconstituted.getId());
        assertEquals(Status.ON_PROGRESS, reconstituted.getStatus());
        assertEquals(1, reconstituted.getOrderItems().size());
        assertEquals(new BigDecimal("100.00"), reconstituted.getTotalPrice());
    }
}
