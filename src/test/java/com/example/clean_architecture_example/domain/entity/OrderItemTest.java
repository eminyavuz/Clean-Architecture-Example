package com.example.clean_architecture_example.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class OrderItemTest {

    @Test
    @DisplayName("Should create OrderItem successfully with valid parameters")
    void should_create_order_item_successfully() {
        OrderItem orderItem = OrderItem.Create(1, "Keyboard", "Wireless Keyboard", new BigDecimal("50.00"), 2);
        assertEquals(1, orderItem.getProductId());
        assertEquals("Keyboard", orderItem.getProductName());
        assertEquals("Wireless Keyboard", orderItem.getDescription());
        assertEquals(new BigDecimal("50.00"), orderItem.getUnitPrice());
        assertEquals(2, orderItem.getQuantity());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when creating OrderItem with quantity <= 0")
    void should_throw_exception_when_quantity_is_zero_or_negative() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> OrderItem.Create(1, "Keyboard", "Description", new BigDecimal("50.00"), 0)
        );
        assertEquals("Quantity must be bigger than zero", exception.getMessage());
    }

    @Test
    @DisplayName("Should calculate total price correctly (unitPrice * quantity)")
    void should_calculate_total_price_correctly() {
        OrderItem orderItem = OrderItem.Create(1, "Keyboard", "Description", new BigDecimal("50.00"), 2);
        assertEquals(new BigDecimal("100.00"), orderItem.getTotalPrice());
    }

    @Test
    @DisplayName("Should increase quantity successfully")
    void should_increase_quantity_successfully() {
        OrderItem orderItem = OrderItem.Create(1, "Keyboard", "Description", new BigDecimal("50.00"), 2);
        orderItem.increaseQuantity(3);
        assertEquals(5, orderItem.getQuantity());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when increase quantity amount is invalid")
    void should_throw_exception_when_increase_quantity_amount_is_invalid() {
        OrderItem orderItem = OrderItem.Create(1, "Keyboard", "Description", new BigDecimal("50.00"), 2);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> orderItem.increaseQuantity(0)
        );
        assertEquals("Amount must be positive", exception.getMessage());
    }
}
