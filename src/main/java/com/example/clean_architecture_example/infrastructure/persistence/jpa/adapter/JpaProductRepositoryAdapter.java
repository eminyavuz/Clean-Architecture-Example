package com.example.clean_architecture_example.infrastructure.persistence.jpa.adapter;

import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.domain.repository.ProductRepository;
import com.example.clean_architecture_example.infrastructure.persistence.mapper.ProductMapper;
import com.example.clean_architecture_example.infrastructure.persistence.repository.ProductJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaProductRepositoryAdapter implements ProductRepository {
    private  final ProductJpaRepository jpaRepository;
    public  JpaProductRepositoryAdapter(
            ProductJpaRepository jpaRepository
    )
    {
        this.jpaRepository=jpaRepository;

    }

    @Override
    public Optional<Product> findById(int productId) {
        return jpaRepository.findById(productId).map(ProductMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll().stream()
                .map(ProductMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void save(Product product) {
        var saved = jpaRepository.save(ProductMapper.toJpa(product));
        if (product.getId() == 0) {
            product.assignId(saved.getId());
        }
    }
}
