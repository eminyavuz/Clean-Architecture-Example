package com.example.clean_architecture_example.infrastructure.persistence.inmemory;

import com.example.clean_architecture_example.domain.entity.Order;
import com.example.clean_architecture_example.domain.repository.OrderRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryOrderRepository implements OrderRepository {
    private  final Map<Integer,Order> storage= new HashMap<>();
    @Override
    public void save(Order order) {
        if (order.getId() == 0) {
            int nextId = storage.keySet().stream().mapToInt(Integer::intValue).max().orElse(0) + 1;
            order.assignId(nextId);
        }
        storage.put(order.getId(), order);
    }

    @Override
    public Optional<Order> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(storage.values());
    }
}
