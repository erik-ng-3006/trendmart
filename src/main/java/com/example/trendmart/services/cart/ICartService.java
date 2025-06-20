package com.example.trendmart.services.cart;

import com.example.trendmart.entities.Cart;
import com.example.trendmart.entities.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}

