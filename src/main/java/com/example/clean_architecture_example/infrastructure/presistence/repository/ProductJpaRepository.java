package com.example.clean_architecture_example.infrastructure.presistence.repository;

import com.example.clean_architecture_example.infrastructure.presistence.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity,Integer>
{
}
