package com.example.clean_architecture_example.infrastructure.persistence.repository;

import com.example.clean_architecture_example.infrastructure.persistence.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity,Integer>
{
}
