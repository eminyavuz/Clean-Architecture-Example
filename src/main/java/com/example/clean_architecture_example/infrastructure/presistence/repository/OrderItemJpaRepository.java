package com.example.clean_architecture_example.infrastructure.presistence.repository;

import com.example.clean_architecture_example.infrastructure.presistence.entity.OrderItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemJpaRepository extends JpaRepository<OrderItemJpaEntity,Integer> {
}
