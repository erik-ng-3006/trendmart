package com.example.trendmart.repositories;

import com.example.trendmart.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
