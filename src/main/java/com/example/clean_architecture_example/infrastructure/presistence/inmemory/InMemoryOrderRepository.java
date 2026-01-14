package com.example.clean_architecture_example.infrastructure.presistence.inmemory;

import com.example.clean_architecture_example.domain.entity.Order;
import com.example.clean_architecture_example.domain.repository.OrderRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryOrderRepository implements OrderRepository {
    private  final Map<Integer,Order> storage= new HashMap<>();
    @Override
    public void save(Order order) {
     storage.put(order.getId(),order);
    }

    @Override
    public Optional<Order> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }
}
