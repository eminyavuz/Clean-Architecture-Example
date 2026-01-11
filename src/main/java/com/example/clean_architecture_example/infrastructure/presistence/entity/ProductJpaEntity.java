package com.example.clean_architecture_example.infrastructure.presistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class ProductJpaEntity {
    @Id
    @GeneratedValue
    int id;

    private  String name;
    private  String  description;
    private BigDecimal price;
    private  boolean isActive;
}
