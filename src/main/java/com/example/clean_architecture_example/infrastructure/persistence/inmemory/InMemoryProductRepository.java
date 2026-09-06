package com.example.clean_architecture_example.infrastructure.persistence.inmemory;

import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.domain.repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<Integer,Product> storage= new HashMap<>();
    @Override
    public Optional<Product> findById(int productId) {
        return Optional.ofNullable(storage.get(productId));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void save(Product product) {
        if (product.getId() == 0) {
            int nextId = storage.keySet().stream().mapToInt(Integer::intValue).max().orElse(0) + 1;
            product.assignId(nextId);
        }
        storage.put(product.getId(), product);
    }
}
