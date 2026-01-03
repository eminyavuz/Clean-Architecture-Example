package com.example.clean_architecture_example.domain.repository;

import com.example.clean_architecture_example.domain.entity.Order;

import java.util.Optional;

public interface OrderRepository {
    void save(Order order);
     Optional<Order> findById(int id);
}
