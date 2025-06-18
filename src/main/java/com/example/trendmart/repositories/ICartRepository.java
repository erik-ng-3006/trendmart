package com.example.trendmart.repositories;

import com.example.trendmart.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
