package com.example.clean_architecture_example.infrastructure.presistence.repository;

import com.example.clean_architecture_example.infrastructure.presistence.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity,Integer> {
}
