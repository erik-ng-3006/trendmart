package com.example.trendmart.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDTO product;
}
