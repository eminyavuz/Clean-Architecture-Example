package com.example.clean_architecture_example.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    @Test
    @DisplayName("Product should be created with valid parameters")
    void should_create_product_successfully(){
        Product product= new Product("Wireless keyboard",new BigDecimal("499.99"),"Description",20,true);
        assertEquals("Wireless keyboard", product.getProductName());
        assertEquals(new BigDecimal("499.99"), product.getPrice());
        assertEquals(20, product.getStock());
        assertTrue(product.isActive());
    }

    @Test
    @DisplayName("Should throw an IllegalArgument exception when price is negative")
    void should_throw_exception_when_price_is_negative()
    {
        IllegalArgumentException exception=assertThrows(
                IllegalArgumentException.class,
                ()->new Product("Keyboard",new BigDecimal("-10"),"Description",10,true)
        );
        assertEquals("Price cannot be smaller than zero",exception.getMessage());
    }

    @Test
    @DisplayName("Should decrease stock successfully")
    void should_decrease_stock_successfully()
    {
        Product product= new Product("Keyboard",new BigDecimal("15"),"Description",10,true);
      product.decreaseStock(3);
      assertEquals(7,product.getStock());
    }

    @Test
    @DisplayName("Should throw an exception when stock is insufficient")
    void should_throw_exception_when_stock_is_insufficient()
    {
        Product product= new Product("Keyboard",new BigDecimal("15"),"Description",5,true);
        IllegalArgumentException exception= assertThrows(
                IllegalArgumentException.class,()->product.decreaseStock(7));

        assertEquals("not enough stock",exception.getMessage());
    }

    // --- Implemented Test Scenarios ---

    @Test
    @DisplayName("Should change product price successfully")
    void should_change_price_successfully() {
        Product product = new Product("Keyboard", new BigDecimal("15.00"), "Description", 10, true);
        product.changePrice(new BigDecimal("25.00"));
        assertEquals(new BigDecimal("25.00"), product.getPrice());
    }

    @Test
    @DisplayName("Should throw exception when changing price to negative value")
    void should_throw_exception_when_new_price_is_negative() {
        Product product = new Product("Keyboard", new BigDecimal("15"), "Description", 10, true);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> product.changePrice(new BigDecimal("-15"))
        );
        assertEquals("Price cannot be smaller than zero", exception.getMessage());
    }

    @Test
    @DisplayName("Should activate and deactivate product successfully")
    void should_activate_and_deactivate_product() {
        Product product = new Product("Keyboard", new BigDecimal("15"), "Description", 10, false);
        product.activate();
        assertTrue(product.isActive());

        product.deactivate();
        assertFalse(product.isActive());
    }

    @Test
    @DisplayName("Should update stock successfully")
    void should_update_stock_successfully() {
        Product product = new Product("Keyboard", new BigDecimal("15"), "Description", 10, true);
        product.updateStock(20);
        assertEquals(20, product.getStock());
    }

    @Test
    @DisplayName("Should throw exception when updating stock with negative value")
    void should_throw_exception_when_updating_stock_with_negative_value() {
        Product product = new Product("Keyboard", new BigDecimal("15"), "Description", 10, true);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> product.updateStock(-6)
        );
        assertEquals("Stock cannot be less than zero", exception.getMessage());
    }

    @Test
    @DisplayName("Should assign ID when ID is not already set")
    void should_assign_id_successfully() {
        Product product = new Product("Keyboard", new BigDecimal("15"), "Description", 10, true);
        product.assignId(5);
        assertEquals(5, product.getId());
    }

    @Test
    @DisplayName("Should throw exception when assigning non-positive ID")
    void should_throw_exception_when_assigning_invalid_id() {
        Product product = new Product("Keyboard", new BigDecimal("15"), "Description", 10, true);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> product.assignId(-6)
        );
        assertEquals("Id must be greater than zero", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when decreasing stock with zero or negative quantity")
    void should_throw_exception_when_decrease_stock_quantity_is_invalid() {
        Product product = new Product("Keyboard", new BigDecimal("15"), "Description", 10, true);
        IllegalArgumentException exception1 = assertThrows(
                IllegalArgumentException.class,
                () -> product.decreaseStock(-6)
        );
        assertEquals("Quantity must be positive", exception1.getMessage());

        IllegalArgumentException exception2 = assertThrows(
                IllegalArgumentException.class,
                () -> product.decreaseStock(0)
        );
        assertEquals("Quantity must be positive", exception2.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when product name is null or blank")
    void should_throw_exception_when_product_name_is_invalid() {
        IllegalArgumentException exception1 = assertThrows(
                IllegalArgumentException.class,
                () -> new Product(null, new BigDecimal("10.00"), "Description", 5, true)
        );
        assertEquals("Product's name cannot be empty", exception1.getMessage());

        IllegalArgumentException exception2 = assertThrows(
                IllegalArgumentException.class,
                () -> new Product("   ", new BigDecimal("10.00"), "Description", 5, true)
        );
        assertEquals("Product's name cannot be empty", exception2.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when description is null or exceeds 255 characters")
    void should_throw_exception_when_description_is_invalid() {
        IllegalArgumentException exception1 = assertThrows(
                IllegalArgumentException.class,
                () -> new Product("Keyboard", new BigDecimal("10.00"), null, 5, true)
        );
        assertEquals("Description cannot be empty or more than 255 characters", exception1.getMessage());

        String longDescription = "a".repeat(256);
        IllegalArgumentException exception2 = assertThrows(
                IllegalArgumentException.class,
                () -> new Product("Keyboard", new BigDecimal("10.00"), longDescription, 5, true)
        );
        assertEquals("Description cannot be empty or more than 255 characters", exception2.getMessage());
    }
}
