package com.example.clean_architecture_example.domain.repository;

import com.example.clean_architecture_example.adapter.web.dto.response.ProductResponse;
import com.example.clean_architecture_example.domain.entity.Product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(int productId);
     void save(Product product);
}
