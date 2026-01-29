package com.example.clean_architecture_example.infrastructure.presistence.jpa.adapter;

import com.example.clean_architecture_example.domain.repository.OrderItemRepository;
import com.example.clean_architecture_example.infrastructure.presistence.repository.OrderItemJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaOrderItemAdapter implements OrderItemRepository {
    private final OrderItemJpaRepository jpaRepository;

    public JpaOrderItemAdapter(OrderItemJpaRepository jpaRepository)
    {
        this.jpaRepository = jpaRepository;
    }
}
