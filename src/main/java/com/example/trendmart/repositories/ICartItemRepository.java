package com.example.trendmart.repositories;

import com.example.trendmart.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);
}

