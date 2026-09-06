package com.example.clean_architecture_example.infrastructure.persistence.repository;

import com.example.clean_architecture_example.infrastructure.persistence.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity,Integer> {
}
