package com.example.clean_architecture_example.domain.repository;

import com.example.clean_architecture_example.domain.entity.Order;

public interface OrderRepository {
    void save(Order order);
    Order findById(int id);
}
