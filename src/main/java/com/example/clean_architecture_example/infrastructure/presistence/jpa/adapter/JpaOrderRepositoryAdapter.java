package com.example.clean_architecture_example.infrastructure.presistence.jpa.adapter;

import com.example.clean_architecture_example.domain.entity.Order;
import com.example.clean_architecture_example.domain.repository.OrderRepository;
import com.example.clean_architecture_example.infrastructure.presistence.repository.OrderItemJpaRepository;
import com.example.clean_architecture_example.infrastructure.presistence.repository.OrderJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaOrderRepositoryAdapter implements OrderRepository {
    private  final OrderJpaRepository jpaRepository;
    public JpaOrderRepositoryAdapter(OrderJpaRepository jpaRepository)
    {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Order order) {

    }

    @Override
    public Optional<Order> findById(int id) {
        return Optional.empty();
    }
}
