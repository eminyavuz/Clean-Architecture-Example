package com.example.clean_architecture_example.infrastructure.persistence.jpa.adapter;

import com.example.clean_architecture_example.domain.entity.Order;
import com.example.clean_architecture_example.domain.repository.OrderRepository;
import com.example.clean_architecture_example.infrastructure.persistence.mapper.OrderMapper;
import com.example.clean_architecture_example.infrastructure.persistence.repository.OrderJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaOrderRepositoryAdapter implements OrderRepository {
    private  final OrderJpaRepository jpaRepository;
    public JpaOrderRepositoryAdapter(OrderJpaRepository jpaRepository)
    {
        this.jpaRepository = jpaRepository;

    }

    @Transactional
    @Override
    public void save(Order order) {
        var saved = jpaRepository.save(OrderMapper.toJpa(order));
        if (order.getId() == 0) {
            order.assignId(saved.getId());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Order> findById(int id) {
        return jpaRepository.findById(id).map(OrderMapper::toDomain);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> findAll() {
        return jpaRepository.findAll().stream()
                .map(OrderMapper::toDomain)
                .collect(Collectors.toList());
    }
}
