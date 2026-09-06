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
    @DisplayName("Should throw an IllegalArgument exception when pirce is negative")
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
    @DisplayName("Should throw an exception when stock is insufficent")
    void should_throw_exception_when_stock_is_insufficent()
    {
        Product product= new Product("Keyboard",new BigDecimal("15"),"Description",5,true);
        IllegalArgumentException exception= assertThrows(
                java.lang.IllegalArgumentException.class,()->product.decreaseStock(7));

        assertEquals("not enough stock",exception.getMessage());
    }
}
