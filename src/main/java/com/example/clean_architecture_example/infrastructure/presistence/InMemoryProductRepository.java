package com.example.clean_architecture_example.infrastructure.presistence;

import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.domain.repository.ProductRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<Integer,Product> storage= new HashMap<>();
    @Override
    public Optional<Product> findById(int productId) {
        return Optional.ofNullable(storage.get(productId));
    }

    @Override
    public void save(Product product) {
        storage.put(product.getId(),product);
    }
}
