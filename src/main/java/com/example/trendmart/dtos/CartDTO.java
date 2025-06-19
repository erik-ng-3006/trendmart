package com.example.trendmart.dtos;

import java.math.BigDecimal;
import java.util.Set;

public class CartDTO {
    private Long cartId;
    private Set<CartItemDTO> items;
    private BigDecimal totalAmount;
}
