package com.example.clean_architecture_example.infrastructure.presistence.jpa.adapter;

import com.example.clean_architecture_example.adapter.web.dto.response.ProductResponse;
import com.example.clean_architecture_example.adapter.web.mapper.ProductMapper;
import com.example.clean_architecture_example.domain.entity.Product;
import com.example.clean_architecture_example.domain.repository.ProductRepository;
import com.example.clean_architecture_example.infrastructure.presistence.repository.ProductJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
    public Optional<ProductResponse> findById(int productId) {

        return jpaRepository.findById(productId).map(ProductMapper::toResponse) ;
    }

    @Override
    public void save(Product product) {

    }
}
