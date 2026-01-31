package com.example.clean_architecture_example.infrastructure.presistence.jpa.adapter;

import com.example.clean_architecture_example.adapter.web.dto.response.OrderResponse;
import com.example.clean_architecture_example.domain.entity.Order;
import com.example.clean_architecture_example.domain.repository.OrderRepository;
import com.example.clean_architecture_example.infrastructure.presistence.entity.OrderJpaEntity;
import com.example.clean_architecture_example.infrastructure.presistence.mapper.OrderMapper;
import com.example.clean_architecture_example.infrastructure.presistence.repository.OrderItemJpaRepository;
import com.example.clean_architecture_example.infrastructure.presistence.repository.OrderJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        OrderJpaEntity entity= OrderMapper.toJpa(order);
        jpaRepository.save(entity);

    }

    @Override
    public Optional<Order> findById(int id) {
return jpaRepository.findById(id)
        .map(OrderMapper::toDomain);
    }
}
